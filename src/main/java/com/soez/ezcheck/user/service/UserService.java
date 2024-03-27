package com.soez.ezcheck.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soez.ezcheck.entity.Users;
import com.soez.ezcheck.security.TokenProvider;
import com.soez.ezcheck.user.SignInResponse;
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

	/**
	 * 회원가입
	 * @author Jihwan
	 * @param userSignUpDTO 사용자가 입력한 ID, 이름, 비밀번호, 비밀번호 확인, 전화번호, 이메일
	 * @return 회원가입 성공여부에 따른 결과 메시지
	 */
	@Transactional
	public List<String> signUp(UserSignUpDTO userSignUpDTO) {

		String uId = userSignUpDTO.getUserId();
		String email = userSignUpDTO.getEmail();
		String phone = userSignUpDTO.getPhone();

		List<String> msg = new ArrayList<>();
		if (usersRepository.existsByUId(uId)) {
			msg.add("이미 가입되어 있는 ID 입니다.");
		}
		if (usersRepository.existsByUEmail(email)) {
			msg.add("이미 가입되어 있는 이메일 입니다.");
		}
		if (usersRepository.existsByUPhone(phone)) {
			msg.add("이미 가입되어 있는 전화번호 입니다.");
		}

		if (!msg.isEmpty()) {
			return msg;
		}

		Users users = new Users();
		users.setUId(userSignUpDTO.getUserId());
		users.setUName(userSignUpDTO.getUsername());
		users.setUPwd(passwordEncoder.encode(userSignUpDTO.getPassword1()));
		users.setUPhone(userSignUpDTO.getPhone());
		users.setUEmail(userSignUpDTO.getEmail());
		this.usersRepository.save(users);
		msg.add(uId + "님 가입을 환영합니다.");

		return msg;
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
	public void deleteAccount(String userId) {
		usersRepository.deleteById(userId);
	}

}
