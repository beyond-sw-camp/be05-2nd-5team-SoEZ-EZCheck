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
    private String name;

    @Column(name = "facility_location")
    private String location;

    @Column(name = "facility_open_hr")
    private String openHr;

    @Column(name = "facility_close_hr")
    private String closeHr;

    @Column(name = "facility_capacity")
    private Integer capacity;

}
