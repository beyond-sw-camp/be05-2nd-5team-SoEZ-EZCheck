package com.soez.ezcheck.reservation.domain;

import java.sql.Date;

import lombok.Getter;

@Getter
public class ReservationRequestDTO {
    private String uId;
    private Integer rgId;
    private Date rvDateFrom;
    private Date rvDateTo;
}
