package com.soez.ezcheck.user.domain;

import lombok.Data;

@Data
public class AccountDeleteDTO {

	private String userId;
	private String password1;
	private String password2;

}
