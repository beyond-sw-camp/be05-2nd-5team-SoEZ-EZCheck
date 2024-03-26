package com.soez.ezcheck.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
@Entity
@Table(name = "check_in")
public class CheckIn {

    @Id
    @Column(name = "cin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cinId;

    @Column(name = "cin_date")
    private Date cinDate;

    @Column(name = "cin_time")
    private Time cinTime;

    @OneToOne
    @JoinColumn(name = "rv_id")
    private Reservation reservation;

    @OneToOne
    @JoinColumn(name = "r_id")
    private Room room;

}
