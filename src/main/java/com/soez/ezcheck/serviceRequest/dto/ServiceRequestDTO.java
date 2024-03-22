package com.soez.ezcheck.serviceRequest.dto;

import com.soez.ezcheck.entity.ServiceRequestStatusEnum;
import com.soez.ezcheck.entity.ServiceRequestTypeEnum;
import com.soez.ezcheck.entity.Users;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ServiceRequestDTO {
    @Enumerated(EnumType.STRING)
    private ServiceRequestTypeEnum serviceRequestTypeEnum;
    @Enumerated(EnumType.STRING)
    private ServiceRequestStatusEnum serviceRequestStatusEnum;
    private String uId;
}
