package com.soez.ezcheck.room.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class RoomSearchRequestDto {
    private Date rsvCheckIn;
    private Date rsvCheckOut;
    private String roomType;
}
