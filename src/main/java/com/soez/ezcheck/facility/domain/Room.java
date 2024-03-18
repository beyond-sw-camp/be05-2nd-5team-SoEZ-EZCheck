package com.soez.ezcheck.facility.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Room")
public class Room {

    @Id
    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "room_type")
    private String type;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_status")
    private RoomStatus roomStatus;

    @Column(name = "room_price")
    private Integer price;

    @Column(name = "room_pwd")
    private String pwd;

}
