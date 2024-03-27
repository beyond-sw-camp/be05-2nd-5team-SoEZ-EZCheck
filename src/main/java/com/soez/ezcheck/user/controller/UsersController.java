package com.soez.ezcheck.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soez.ezcheck.user.SignInResponse;
import com.soez.ezcheck.user.domain.EmailCheckDTO;
import com.soez.ezcheck.user.domain.UserSignInDTO;
import com.soez.ezcheck.user.domain.UserSignUpDTO;
import com.soez.ezcheck.user.service.MailService;
import com.soez.ezcheck.user.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersController {

	private final UserService userService;
	private final MailService mailService;

	/**
	 * 사용자로부터 입력받은 값들로 회원가입을 진행. 비밀번호와 비밀번호 확인(재입력)이 다를경우 오류메시지 반환.
	 * @author Jihwan
	 * @param userSignUpDTO 사용자가 입력한 ID, 이름, 비밀번호, 비밀번호 확인, 전화번호, 이메일
	 * @return 회원가입 성공여부에 따른 결과 메시지
	 */
	@PostMapping("/signup")
	public ResponseEntity<List<String>> signUp(@Valid @RequestBody UserSignUpDTO userSignUpDTO) {
		List<String> msg = new ArrayList<>();
		if (!userSignUpDTO.getPassword1().equals(userSignUpDTO.getPassword2())) {
			msg.add("2개의 패스워드가 일치하지 않습니다.");
			return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
		}

		mailService.setEmail(userSignUpDTO.getEmail());

		msg.add("이메일로 전송된 인증 코드를 확인하고 입력해주세요.");
		return new ResponseEntity<>(msg, HttpStatus.OK);
		// 인증 코드를 사용자에게 전송하고, 입력받을 수 있는 페이지로 리다이렉트
	}

	/**
	 * 사용자로부터 인증코드를 입력받아, 전송된 인증코드와 일치한지 확인하여 회원가입 진행
	 * @author Jihwan
	 * @param emailCheckDTO 사용자가 입력한 이메일 주소와 인증코드
	 * @return 인증 성공여부에 따른 결과 메시지
	 */
	@GetMapping("/verify")
	public ResponseEntity<String> verifyEmail(@RequestBody EmailCheckDTO emailCheckDTO) {

		String email = emailCheckDTO.getEmail();
		String authCode = emailCheckDTO.getAuthCode();

		if (mailService.checkAuthNumber(email, authCode)) {
			return new ResponseEntity<>("인증에 성공하였습니다.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("인증 코드가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 사용자로부터 입력받은 값들로 회원가입을 진행
	 * @author Jihwan
	 * @param userSignUpDTO 사용자가 입력한 ID, 이름, 비밀번호, 비밀번호 확인, 전화번호, 이메일
	 * @return 회원가입 성공여부에 따른 결과 메시지
	 */
	@PostMapping("/register")
	public ResponseEntity<List<String>> register(@Valid @RequestBody UserSignUpDTO userSignUpDTO) {
		List<String> msg = userService.signUp(userSignUpDTO);
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}

	@Tag(name = "User")
	@GetMapping("/signin")
	public ResponseEntity<SignInResponse> signIn(@RequestBody UserSignInDTO userSignInDTO) {
		SignInResponse msg = userService.signIn(userSignInDTO);
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}

	/**
	 * 사용자로부터 입력받은 ID와 이메일로 비밀번호 재설정
	 * @author Jihwan
	 * @param request 사용자가 입력한 ID, 이메일, 새로운 비밀번호
	 * @return 비밀번호 재설정 성공여부에 따른 결과 메시지
	 */
	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
		String userId = request.get("userId");
		String email = request.get("email");

		if (!userService.existsByUIdAndUEmail(userId, email)) {
			return new ResponseEntity<>("입력하신 ID 또는 이메일이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
		}

		mailService.setEmail(email);

		return new ResponseEntity<>("이메일로 전송된 인증 코드를 확인하고 입력해주세요.", HttpStatus.OK);
		// 인증 코드를 사용자에게 전송하고, 입력받을 수 있는 페이지로 리다이렉트
	}

	/**
	 * 사용자로부터 인증코드를 입력받아, 전송된 인증코드와 일치한지 확인하여 비밀번호 재설정 진행
	 * @author Jihwan
	 * @param request 사용자가 입력한 이메일 주소, 인증코드, 새로운 비밀번호
	 * @return 비밀번호 재설정 성공여부에 따른 결과 메시지
	 */
	@PostMapping("/newpassword")
	public ResponseEntity<String> verifyResetPassword(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		String authCode = request.get("authCode");
		String newPassword = request.get("newPassword");

		if (mailService.checkAuthNumber(email, authCode)) {
			userService.resetPassword(email, newPassword);
			return new ResponseEntity<>("비밀번호가 성공적으로 재설정되었습니다.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("인증 코드가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
		}
	}

}
