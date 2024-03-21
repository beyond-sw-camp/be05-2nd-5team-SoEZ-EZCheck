package com.soez.ezcheck.room.domain;

import java.sql.Date;

import com.soez.ezcheck.entity.RoomGradeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class AvailableRoomsRequestDTO {
    private Date rvDateFrom;
    private Date rvDateTo;

    @Enumerated(EnumType.STRING)
    private RoomGradeEnum roomGradeEnum;
}
