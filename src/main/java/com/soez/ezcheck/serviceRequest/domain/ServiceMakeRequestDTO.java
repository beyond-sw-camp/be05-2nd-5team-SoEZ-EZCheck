package com.soez.ezcheck.serviceRequest.domain;


public class ServiceMakeRequestDTO {
    private String uId;

    public ServiceMakeRequestDTO() {
    }
    public ServiceMakeRequestDTO(String uId) {
        this.uId = uId;
    }
    public String getuId() {
        return uId;
    }
    public void setuId(String uId) {
        this.uId = uId;
    }
    
}
