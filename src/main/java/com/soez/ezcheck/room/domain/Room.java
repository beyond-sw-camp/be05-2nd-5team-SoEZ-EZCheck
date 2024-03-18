package com.soez.ezcheck.room.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Room {
    
    @Id
    private Integer     roomId;

    private String      roomType;
    private Integer     roomPrice;

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;
    
    private String      roomPwd;

}