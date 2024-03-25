package com.soez.ezcheck.reservation.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDTO {

    private String uId;
    private Integer rgId;
    private Date rvDateFrom;
    private Date rvDateTo;
}
