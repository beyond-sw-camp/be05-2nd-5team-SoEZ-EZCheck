package com.soez.ezcheck.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "service_request")
public class ServiceRequest {

    @Id
    @Column(name = "rq_id")
    private Integer rqId;

    @Column(name = "rq_type")
    @Enumerated(EnumType.STRING)
    private ServiceRequestTypeEnum serviceRequestTypeEnum;

    @Column(name = "rq_status")
    @Enumerated(EnumType.STRING)
    private ServiceRequestStatusEnum serviceRequestStatusEnum;

    @ManyToOne
    @JoinColumn("u_id")
    private User user;

}
