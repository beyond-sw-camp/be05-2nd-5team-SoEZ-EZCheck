package com.soez.ezcheck.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
@Entity
@Table(name = "checkout")
public class CheckOut {

    @Id
    @Column(name = "cout_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer coutId;

    @Column(name = "cout_status")
    @Enumerated(EnumType.STRING)
    private CheckOutStatusEnum checkOutStatusEnum;

    @Column(name = "cout_date")
    private Date coutDate;

    @Column(name = "cout_time")
    private Time coutTime;

    @ManyToOne
    @JoinColumn(name = "u_id")
    private Users users;

    @OneToOne
    @JoinColumn(name = "cin_id")
    private CheckIn checkIn;

}
