package com.soez.ezcheck.reservation.domain;

import java.sql.Date;

import com.soez.ezcheck.user.domain.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer     rsvId;
    
    @ManyToOne
    @JoinColumn(name = "userId")
    private User        user;

    private String      roomType;

    private Date        rsvCheckIn;

    private Date        rsvCheckOut;
    
    @Enumerated(EnumType.STRING)
    private RsvStatus      rsvStatus;
}
