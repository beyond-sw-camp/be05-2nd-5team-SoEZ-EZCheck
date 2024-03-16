package com.soez.ezcheck.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    private String      userId;
    
    private String      userPwd;
    private String      userName;


    private String      userEmail;
    private String      userPhone;

}
