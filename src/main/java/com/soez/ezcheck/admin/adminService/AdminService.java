package com.soez.ezcheck.admin.adminService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soez.ezcheck.admin.adminRepository.AdminRepository;
import com.soez.ezcheck.entity.Admin;
import com.soez.ezcheck.entity.Users;
import com.soez.ezcheck.security.TokenProvider;
import com.soez.ezcheck.user.SignInResponse;
import com.soez.ezcheck.user.domain.UserSignInDTO;
import com.soez.ezcheck.user.domain.UserSignUpDTO;

import lombok.RequiredArgsConstructor;
import java.util.*;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;


    @Transactional
	public List<String> signUp(UserSignUpDTO userSignUpDTO) {

		String uId = userSignUpDTO.getUserId();
		String email = userSignUpDTO.getEmail();
		String phone = userSignUpDTO.getPhone();

		List<String> msg = new ArrayList<>();
		if (adminRepository.existsByUId(uId)) {
			msg.add("이미 가입되어 있는 ID 입니다.");
		}
		if (adminRepository.existsByUEmail(email)) {
			msg.add("이미 가입되어 있는 이메일 입니다.");
		}
		if (adminRepository.existsByUPhone(phone)) {
			msg.add("이미 가입되어 있는 전화번호 입니다.");
		}

		if (!msg.isEmpty()) {
			return msg;
		}

		Admin admin = new Admin();
		admin.setUId(userSignUpDTO.getUserId());
		admin.setUName(userSignUpDTO.getUsername());
		admin.setUPwd(passwordEncoder.encode(userSignUpDTO.getPassword1()));
		admin.setUPhone(userSignUpDTO.getPhone());
		admin.setUEmail(userSignUpDTO.getEmail());
		this.adminRepository.save(admin);
		msg.add(uId + "님 가입을 환영합니다.");

		return msg;
	}


    @Transactional
	public SignInResponse signIn(UserSignInDTO userSignInDTO) {
		Optional<Admin> admin = adminRepository.findById(userSignInDTO.getUserId());
		if (admin.isPresent()) {
			if (passwordEncoder.matches(userSignInDTO.getPassword(), admin.get().getUPwd())) {
				String token = tokenProvider.createToken(String.format("%s:%s", admin.get().getUId(), "Admin"));
				return new SignInResponse(admin.get().getUId(), "Admin", token);
			}
		}
		return null;
	}
}
