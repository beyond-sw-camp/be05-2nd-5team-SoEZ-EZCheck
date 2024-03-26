package com.soez.ezcheck.user;

import com.soez.ezcheck.entity.Users;

public record SignInResponse(String userId, String type, String token) {
}
