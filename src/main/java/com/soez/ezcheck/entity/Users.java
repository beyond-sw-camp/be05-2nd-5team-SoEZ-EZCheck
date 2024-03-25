package com.soez.ezcheck.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class Users {

	@Id
	@Column(name = "u_id")
	private String uId;

	@Column(name = "u_pwd")
	private String uPwd;

	@Column(name = "u_name")
	private String uName;

	@Column(name = "u_email")
	private String uEmail;

	@Column(name = "u_phone")
	private String uPhone;

}
