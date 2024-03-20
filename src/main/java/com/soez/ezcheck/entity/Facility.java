package com.soez.ezcheck.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "facility")
public class Facility {

    @Id
    @Column(name = "f_id")
    private Integer fId;

    @Column(name = "f_name")
    private String fName;

    @Column(name = "f_capacity")
    private Integer fCapacity;

    @Column(name = "f_status")
    @Enumerated(EnumType.STRING)
    private FacilityStatusEnum facilityStatusEnum;

}
