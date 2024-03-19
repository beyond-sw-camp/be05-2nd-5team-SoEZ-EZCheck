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
    private Integer cinId;

    @Column("cin_date")
    private Date cinDate;

    @Column("cin_time")
    private Time cinTime;

    @OneToOne
    @JoinColumn("rv_id")
    private Reservation reservation;

    @OneToOne
    @JoinColumn("r_id")
    private Room room;

}
