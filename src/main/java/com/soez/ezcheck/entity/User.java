package com.soez.ezcheck.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "u_id")
    private String uId;

    @Column(name = "u_pwd")
    private String uPwd;

    @Column("u_name")
    private String uName;

    @Column("u_email")
    private String uEmail;

    @Column("u_phone")
    private String uPhone;

}
