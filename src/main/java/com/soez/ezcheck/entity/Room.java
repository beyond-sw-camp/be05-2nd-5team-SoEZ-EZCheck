package com.soez.ezcheck.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "r_id")
    private Integer rId;

    @Column(name = "r_status")
    @Enumerated(EnumType.STRING)
    private RoomStatusEnum roomStatusEnum;

    @Column(name = "r_pwd")
    private String rPwd;

    @ManyToOne
    @JoinColumn(name = "rg_id")
    private RoomGrade roomGrade;

}
