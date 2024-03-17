package com.soez.ezcheck.facility.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FacilityReservationId implements Serializable {

    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "facility_id")
    private Integer facilityId;

}
