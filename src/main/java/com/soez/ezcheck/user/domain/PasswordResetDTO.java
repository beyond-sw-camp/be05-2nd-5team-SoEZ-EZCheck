package com.soez.ezcheck.user.domain;

import lombok.Data;

@Data
public class PasswordResetDTO {

	private String userId;
	private String email;
	private String authCode;
	private String newPassword;

}
