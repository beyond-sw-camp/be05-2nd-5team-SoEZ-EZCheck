package com.soez.ezcheck.facility.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
public class FacilityReservationRequestDTO {

    private Integer facilityId;
    private String userId;
    private Date date;
    private Time time;
    private Integer peopleToReserve;

}
