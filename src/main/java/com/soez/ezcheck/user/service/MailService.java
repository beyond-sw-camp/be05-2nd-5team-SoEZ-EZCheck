package com.soez.ezcheck.user.service;

import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.soez.ezcheck.config.RedisUtil;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender javaMailSender;
	private final RedisUtil redisUtil;
	private static int authNumber;

	/**
	 * 임의의 6자리 인증코드를 생성
	 * @author Jihwan
	 */
	public static void makeRandomNumber() {
		Random r = new Random();
		StringBuilder randomNumber = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			randomNumber.append(r.nextInt(10));
		}

		authNumber = Integer.parseInt(randomNumber.toString());
	}

	/**
	 * 메일 제목, 내용, 보내는이, 쓰는이 등 세부사항을 설정
	 * @author Jihwan
	 * @param email 인증메일을 보낼 이메일 주소
	 * @return 6자리 인증코드
	 */
	public String setEmail(String email) {
		makeRandomNumber();
		String fromEmail = "leyqorwlghks@gmail.com";
		String toEmail = email;
		String title = "본인확인 인증 이메일 입니다.";
		// String content =
		// 	"이지스테이를 방문해주셔서 감사합니다." +
		// 		"<br><br>" +
		// 		"인증 번호는 " + authNumber + "입니다." +
		// 		"<br>" +
		// 		"인증번호를 정확하게 입력해주세요 :)";
		String body = "";
		body += "<h3>" + "이지스테이를 방문해주셔서 감사합니다." + "</h3>";
		body += "<h1>" + "인증번호는 " + authNumber + " 입니다." + "</h1>";
		body += "<h3>" + "인증번호를 정확하게 입력해주세요 :)" + "</h3>";
		sendMail(fromEmail, toEmail, title, body);
		return Integer.toString(authNumber);
	}

	/**
	 * 설정해둔 세부사항 정보를 가지고 인증메일을 전송
	 * @author Jihwan
	 * @param fromEmail 보내는이 이메일 주소
	 * @param toEmail 받는이 이메일 주소
	 * @param title 메일 제목
	 * @param content 메일 내용
	 */
	public void sendMail(String fromEmail, String toEmail, String title, String content) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(fromEmail);
			helper.setTo(toEmail);
			helper.setSubject(title);
			helper.setText(content, true);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		redisUtil.setDataExpire(Integer.toString(authNumber), toEmail, 60 * 5L);
	}

	/**
	 * 사용자가 입력한 인증번호가 전송된 인증번호와 일치하는지 확인
	 * @author Jihwan
	 * @param email 사용자가 인증번호를 받은 이메일
	 * @param authNumber 사용자가 해당 이메일로 받은 인증번호
	 * @return 사용자가 입력한 인증번호와 전송됐었던 인증번호가 일치하는지 여부
	 */
	public boolean checkAuthNumber(String email, String authNumber) {
		if (redisUtil.getData(authNumber) == null) {
			return false;
		} else
			return redisUtil.getData(authNumber).equals(email);
	}

}
