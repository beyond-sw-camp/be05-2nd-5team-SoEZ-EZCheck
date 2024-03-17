package com.soez.ezcheck.room.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class RoomSearchRequestDto {
    private Date rsvCheckIn;
    private Date rsvCheckOut;
    private String roomType;
}
