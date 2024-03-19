package com.soez.ezcheck.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table("room")
public class Room {

    @Id
    @Column(name = "r_id")
    private Integer rId;

    @ManyToOne
    @JoinColumn(name = "rg_id")
    private RoomGrade roomGrade;

    @Column(name = "r_status")
    @Enumerated(EnumType.STRING)
    private RoomStatusEnum roomStatusEnum;

    @Column(name = "room_pwd")
    private String rPwd;

}
