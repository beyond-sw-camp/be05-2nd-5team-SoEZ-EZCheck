package com.soez.ezcheck.user.domain;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignInDTO {
    @Size(min = 3, max = 255)
	private String userId;

    @Size(min = 3, max = 255)
	private String password;
}
