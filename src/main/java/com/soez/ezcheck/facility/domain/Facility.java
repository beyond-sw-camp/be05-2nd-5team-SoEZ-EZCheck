package com.soez.ezcheck.facility.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Facility")
public class Facility {

    @Id
    @Column(name = "facility_id")
    private Integer facilityId;

    @Column(name = "facility_name")
    private String facilityName;

    @Column(name = "facility_location")
    private String facilityLocation;

    @Column(name = "facility_open_hr")
    private String facilityOpenHr;

    @Column(name = "facility_close_hr")
    private String facilityCloseHr;

    @Column(name = "facility_capacity")
    private Integer facilityCapacity;

}
