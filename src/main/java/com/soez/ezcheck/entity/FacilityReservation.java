package com.soez.ezcheck.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Data
@Entity
@Table(name = "facility_reservation")
public class FacilityReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fr_id")
    private Integer frId;

    @Column(name = "fr_date")
    private Date frDate;

    @Column(name = "fr_time")
    private Time frTime;

    @Column(name = "fr_people_num")
    private Integer frPeopleNum;

    @ManyToOne
    @JoinColumn(name = "f_id")
    private Facility facility;

    @ManyToOne
    @JoinColumn(name = "u_id")
    private Users users;

}
