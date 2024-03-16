package com.soez.ezcheck.user.entity;

import java.util.List;

import com.soez.ezcheck.reservation.entity.Reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    private String      userId;
    
    private String      userPwd;
    private String      userName;


    private String      userEmail;
    private String      userPhone;

    @OneToMany
    @JoinColumn(name="userId")
    private List<Reservation> reservations;
}
