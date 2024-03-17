package com.soez.ezcheck.facility.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FacilityReservation")
public class FacilityReservation {

    @EmbeddedId
    private FacilityReservationId id;

    @Column(name = "fr_people_no")
    private Integer frPeopleNo;

    @Column(name = "fr_rsv_date")
    private Date frRsvDate;

    @Column(name = "fr_rsv_time_from")
    private Time frRsvTimeFrom;

    @Column(name = "fr_rsv_time_to")
    private Time frRsvTimeTo;

}
