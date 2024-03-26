package com.soez.ezcheck.facility.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
public class FacilityReservationDetailsDTO {

    private String facilityName;
    private Date reservationDate;
    private Time reservationTime;
    private Integer numberOfPeople;

}
