package com.soez.ezcheck.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soez.ezcheck.entity.Users;
import com.soez.ezcheck.security.TokenProvider;
import com.soez.ezcheck.user.SignInResponse;
import com.soez.ezcheck.user.domain.UserSignInDTO;
import com.soez.ezcheck.user.domain.UserSignUpDTO;
import com.soez.ezcheck.user.repository.UsersRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;

	/**
	 * 사용자로부터 입력받은 값들로 회원가입을 진행.
	 * ID, 이메일, 전화번호 중 중복값이 존재할 경우 오류 메시지 반환.
	 * @author Jihwan
	 * @param userSignUpDTO ID, 이름, 비밀번호, 비밀번호 확인, 전화번호, 이메일을 포함하는 DTO
	 * @return 가입 성공여부에 따른 결과 메시지
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
	public SignInResponse signIn(UserSignInDTO UserSignInDTO){
		Optional<Users> users = usersRepository.findById(UserSignInDTO.getUserId());
		if(users.isPresent()){
			if(passwordEncoder.matches(UserSignInDTO.getPassword(), users.get().getUPwd())){
				String token = tokenProvider.createToken(String.format("%s:%s", users.get().getUId(), "User"));
				return new SignInResponse(users.get().getUId() , "User", token);
			}
		}
		return null;
	}
}
