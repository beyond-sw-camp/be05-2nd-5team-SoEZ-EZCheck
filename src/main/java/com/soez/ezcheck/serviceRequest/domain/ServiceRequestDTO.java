package com.soez.ezcheck.serviceRequest.domain;

import com.soez.ezcheck.entity.ServiceRequestStatusEnum;
import com.soez.ezcheck.entity.ServiceRequestTypeEnum;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;



public class ServiceRequestDTO {
    @Enumerated(EnumType.STRING)
    private ServiceRequestTypeEnum serviceRequestTypeEnum;
    @Enumerated(EnumType.STRING)
    private ServiceRequestStatusEnum serviceRequestStatusEnum;
    private String uId;

    public ServiceRequestDTO() {
    }
    public ServiceRequestDTO(String uId) {
        this.uId = uId;
    }
    public ServiceRequestDTO (String uId, ServiceRequestTypeEnum serviceRequestTypeEnum, ServiceRequestStatusEnum serviceRequestStatusEnum){
        this.uId = uId;
        this.serviceRequestStatusEnum = serviceRequestStatusEnum;
        this.serviceRequestTypeEnum = serviceRequestTypeEnum;
    }
    public ServiceRequestTypeEnum getServiceRequestTypeEnum() {
        return serviceRequestTypeEnum;
    }
    public void setServiceRequestTypeEnum(ServiceRequestTypeEnum serviceRequestTypeEnum) {
        this.serviceRequestTypeEnum = serviceRequestTypeEnum;
    }
    public ServiceRequestStatusEnum getServiceRequestStatusEnum() {
        return serviceRequestStatusEnum;
    }
    public void setServiceRequestStatusEnum(ServiceRequestStatusEnum serviceRequestStatusEnum) {
        this.serviceRequestStatusEnum = serviceRequestStatusEnum;
    }
    public String getuId() {
        return uId;
    }
    public void setuId(String uId) {
        this.uId = uId;
    }
    
}
