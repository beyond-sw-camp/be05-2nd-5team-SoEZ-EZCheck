package com.soez.ezcheck.room.entity;

import com.soez.ezcheck.reservation.entity.Reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Room {
    
    @Id
    private Integer     roomId;

    private String      roomType;
    private Integer     roomPrice;
    private enum        roomStatus{
        available, occupied, maintenance
    }
    private String      roomPwd;

    @OneToMany
    @JoinColumn(name="roomId")
    private Reservation reservation;
}
