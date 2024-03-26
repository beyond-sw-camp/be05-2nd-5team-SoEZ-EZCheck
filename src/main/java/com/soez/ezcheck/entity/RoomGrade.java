package com.soez.ezcheck.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "room_grade")
public class RoomGrade {

    @Id
    @Column(name = "rg_id")
    private Integer rgId;

    @Column(name = "rg_name")
    @Enumerated(EnumType.STRING)
    private RoomGradeEnum roomGradeEnum;

}
