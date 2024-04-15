package com.soez.ezcheck.user.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpDTO {

	@Size(min = 3, max = 255)
	@NotEmpty(message = "사용자 아이디는 필수항목 입니다.")
	private String userId;

	@Size(max = 255)
	@NotEmpty(message = "이름은 필수항목 입니다.")
	private String username;

	@Size(min = 3, max = 255)
	@NotEmpty(message = "비밀번호는 필수항목 입니다.")
	private String password1;

	@Size(min = 3, max = 255)
	private String password2;

	@Size(max = 40)
	@NotEmpty(message = "전화번호는 필수항목 입니다.")
	private String phone;

	@Email
	@Size(max = 255)
	@NotEmpty(message = "이메일은 필수항목 입니다.")
	private String email;

}
