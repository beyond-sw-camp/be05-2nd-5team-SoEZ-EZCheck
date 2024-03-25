package com.soez.ezcheck.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soez.ezcheck.user.domain.UserSignUpDTO;
import com.soez.ezcheck.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersController {

	private final UserService userService;

	/**
	 * 사용자로부터 입력받은 값들로 회원가입을 진행. 비밀번호와 비밀번호 확인(재입력)이 다를경우 오류메시지 반환.
	 * @author Jihwan
	 * @param userSignUpDTO ID, 이름, 비밀번호, 비밀번호 확인, 전화번호, 이메일을 포함하는 DTO
	 * @return 가입 성공여부에 따른 결과 메시지
	 */
	@PostMapping("/signup")
	public ResponseEntity<List<String>> signUp(@Valid @RequestBody UserSignUpDTO userSignUpDTO) {
		System.out.println("debug >>> 로그인 컨트롤러");
		List<String> msg = new ArrayList<>();
		if (!userSignUpDTO.getPassword1().equals(userSignUpDTO.getPassword2())) {
			msg.add("2개의 패스워드가 일치하지 않습니다.");
			return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
		}

		msg = userService.signUp(userSignUpDTO);
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}

}
