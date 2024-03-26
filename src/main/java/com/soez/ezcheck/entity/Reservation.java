package com.soez.ezcheck.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "reservation")
public class Reservation {

	@Id
	@Column(name = "rv_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer rvId;

	@Column(name = "rv_date_from")
	private Date rvDateFrom;

	@Column(name = "rv_date_to")
	private Date rvDateTo;

	@ManyToOne
	@JoinColumn(name = "u_id")
	private Users users;

    @ManyToOne
    @JoinColumn(name = "rg_id")
    private RoomGrade roomGrade;

}
