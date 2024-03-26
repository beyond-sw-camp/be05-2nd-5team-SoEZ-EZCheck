package com.soez.ezcheck.user.controller;

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
	 * 인증 이메일을 전송
	 * @author Jihwan
	 * @param emailRequestDTO 사용자가 입력한 인증할 이메일 주소
	 * @return 6자리 인증코드
	 */
	@PostMapping("/send")
	public String mailSend(@RequestBody @Valid EmailRequestDTO emailRequestDTO) {
		return mailService.setEmail(emailRequestDTO.getEmail());
	}

	/**
	 * 사용자로부터 인증번호를 입력받아, 전송된 인증번호와 일치한지 확인
	 * @author Jihwan
	 * @param emailCheckDTO 사용자 이메일 주소와 인증절차를 거칠 사용자가 입력한 6자리 인증코드
	 * @return 인증 성공여부에 따른 결과 메시지
	 */
	@PostMapping("/check")
	public String authCheck(@RequestBody @Valid EmailCheckDTO emailCheckDTO) {
		boolean Checked = mailService.checkAuthNumber(emailCheckDTO.getEmail(), emailCheckDTO.getAuthNumber());
		if (Checked) {
			return "인증되었습니다.";
		} else {
			throw new NullPointerException("인증을 다시 진행해 주세요.");
		}
	}

}
