package com.soez.ezcheck.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soez.ezcheck.checkIn.repository.CheckInRepository;
import com.soez.ezcheck.entity.Reservation;
import com.soez.ezcheck.entity.Users;
import com.soez.ezcheck.facility.repository.FacilityReservationRepository;
import com.soez.ezcheck.reservation.repository.ReservationRepository;
import com.soez.ezcheck.security.TokenProvider;
import com.soez.ezcheck.user.SignInResponse;
import com.soez.ezcheck.user.domain.UserInfoDTO;
import com.soez.ezcheck.user.domain.UserSignInDTO;
import com.soez.ezcheck.user.domain.UserSignUpDTO;
import com.soez.ezcheck.user.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;
	private final FacilityReservationRepository facilityReservationRepository;
	private final CheckInRepository checkInRepository;
	private final ReservationRepository reservationRepository;

	/**
	 * 회원가입
	 * @author Jihwan
	 * @param userSignUpDTO 사용자가 입력한 ID, 이름, 비밀번호, 비밀번호 확인, 전화번호, 이메일
	 * @return 회원가입 성공여부에 따른 결과 메시지
	 */
	@Transactional
	public String signUp(UserSignUpDTO userSignUpDTO) {

		Users users = new Users();
		users.setUId(userSignUpDTO.getUserId());
		users.setUName(userSignUpDTO.getUsername());
		users.setUPwd(passwordEncoder.encode(userSignUpDTO.getPassword1()));
		users.setUPhone(userSignUpDTO.getPhone());
		users.setUEmail(userSignUpDTO.getEmail());
		this.usersRepository.save(users);

		return userSignUpDTO.getUserId() + "님 가입을 환영합니다.";
	}

	/**
	 * 사용자 ID 중복확인
	 * @author Jihwan
	 * @param userId 사용자 ID
	 * @return 사용자 ID 중복여부
	 */
	public boolean existsByUId(String userId) {
		return usersRepository.existsByUId(userId);
	}

	/**
	 * 사용자 이메일 중복확인
	 * @param email 사용자 이메일
	 * @return 사용자 이메일 중복여부
	 */
	public boolean existsByUEmail(String email) {
		return usersRepository.existsByUEmail(email);
	}

	@Transactional
	public SignInResponse signIn(UserSignInDTO userSignInDTO) {
		Optional<Users> users = usersRepository.findById(userSignInDTO.getUserId());
		if (users.isPresent()) {
			if (passwordEncoder.matches(userSignInDTO.getPassword(), users.get().getUPwd())) {
				String token = tokenProvider.createToken(String.format("%s:%s", users.get().getUId(), "User"));
				return new SignInResponse(users.get().getUId(), "User", token);
			}
		}
		return null;
	}

	/**
	 * 입력받은 사용자 ID와 이메일로 사용자 정보가 존재하는지 확인
	 * @author Jihwan
	 * @param userId 사용자 ID
	 * @param email 사용자 이메일
	 * @return 사용자 정보 존재 여부
	 */
	public boolean existsByUIdAndUEmail(String userId, String email) {
		return usersRepository.existsByUIdAndUEmail(userId, email);
	}

	/**
	 * 사용자로부터 입력받은 이메일로 비밀번호 재설정
	 * @author Jihwan
	 * @param email 사용자 이메일
	 * @param newPassword 새로운 비밀번호
	 */
	public void resetPassword(String email, String newPassword) {
		Users users = usersRepository.findByUEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException(email + "로 가입된 사용자가 없습니다."));
		users.setUPwd(passwordEncoder.encode(newPassword));
		usersRepository.save(users);
	}

	/**
	 * 사용자로부터 입력받은 아이디와 비밀번호가 일치하는지 확인
	 * @author Jihwan
	 * @param userId 사용자 ID
	 * @param password 사용자 비밀번호
	 * @return 사용자 비밀번호 일치 여부
	 */
	public boolean checkPassword(String userId, String password) {
		Users user = usersRepository.findById(userId)
			.orElseThrow(() -> new UsernameNotFoundException(userId + "와 일치하는 사용자가 없습니다."));
		return passwordEncoder.matches(password, user.getUPwd());
	}

	/**
	 * 사용자 계정 삭제
	 * @author Jihwan
	 * @param userId 삭제할 사용자 ID
	 */
	@Transactional
	public void deleteAccount(String userId) {
		Optional<Users> users = usersRepository.findById(userId);
		if (users.isEmpty()) {
			throw new UsernameNotFoundException(userId + "와 일치하는 사용자가 없습니다.");
		}
		facilityReservationRepository.deleteByUserId(users.get().getUId());
		usersRepository.deleteById(userId);
	}

	/**
	 * 사용자 계정 삭제
	 * @param userId 사용자 ID
	 * @param password 사용자 비밀번호
	 */
	@Transactional
	public void deleteAccount(String userId, String password) {
		Users user = usersRepository.findById(userId)
			.orElseThrow(() -> new UsernameNotFoundException(userId + "와 일치하는 사용자가 없습니다."));

		if (!passwordEncoder.matches(password, user.getUPwd())) {
			throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
		}

		List<Reservation> reservations = reservationRepository.findByUsers(user);
		for (Reservation reservation : reservations) {
			checkInRepository.deleteByReservationId(reservation.getRvId());
		}

		facilityReservationRepository.deleteByUserId(user.getUId());
		reservationRepository.deleteByUserId(user.getUId());
		usersRepository.delete(user);
	}

	/**
	 * 사용자 정보 조회
	 * @author Jihwan
	 * @param userId 사용자 ID
	 * @return 사용자 ID, 이름, 전화번호, 이메일을 포함한 사용자 정보
	 */
	public UserInfoDTO getUserInfo(String userId) {
		Users user = usersRepository.findById(userId)
			.orElseThrow(() -> new UsernameNotFoundException(userId + "와 일치하는 사용자가 없습니다."));
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		userInfoDTO.setUserId(user.getUId());
		userInfoDTO.setName(user.getUName());
		userInfoDTO.setPhoneNumber(user.getUPhone());
		userInfoDTO.setEmail(user.getUEmail());
		return userInfoDTO;
	}

}
