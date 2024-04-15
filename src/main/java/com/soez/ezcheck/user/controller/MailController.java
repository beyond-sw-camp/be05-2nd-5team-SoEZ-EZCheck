package com.soez.ezcheck.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soez.ezcheck.user.domain.EmailCheckDTO;
import com.soez.ezcheck.user.domain.EmailRequestDTO;
import com.soez.ezcheck.user.service.MailService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user/auth")
@RequiredArgsConstructor
public class MailController {

	private final MailService mailService;

	/**
	 * 사용자가 입력한 이메일 주소로 인증코드를 전송
	 * @author Jihwan
	 * @param emailRequestDTO 사용자가 입력한 이메일 주소
	 * @return ResponseEntity<Void>
	 */
	@PostMapping("/send")
	public ResponseEntity<Void> mailSend(@RequestBody @Valid EmailRequestDTO emailRequestDTO) {
		mailService.setEmail(emailRequestDTO.getEmail());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * 사용자로부터 인증번호를 입력받아, 전송된 인증번호와 일치한지 확인
	 * @author Jihwan
	 * @param emailCheckDTO 사용자가 입력한 이메일 주소와 8자리 인증코드
	 * @return 인증 성공여부에 따른 결과 메시지
	 */
	@PostMapping("/check")
	public ResponseEntity<Boolean> authCheck(@RequestBody @Valid EmailCheckDTO emailCheckDTO) {
		boolean result = mailService.checkAuthNumber(emailCheckDTO.getEmail(), emailCheckDTO.getAuthCode());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
