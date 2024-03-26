package com.soez.ezcheck.reservation.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ReservationRequestDTO {

    private String uId;
    private Integer rgId;
    private Date rvDateFrom;
    private Date rvDateTo;

    public ReservationRequestDTO() {
    }
    public ReservationRequestDTO(String uId){
        this.uId = uId;
    }
    public ReservationRequestDTO(Date rvDateFrom, Date rvDateTo){
        this.rvDateFrom = rvDateFrom;
        this.rvDateTo = rvDateTo;
    }
    public ReservationRequestDTO(String uId, Integer rgId, Date rvDateFrom, Date rvDateTo){
        this.uId = uId;
        this.rgId = rgId;
        this.rvDateFrom = rvDateFrom;
        this.rvDateTo = rvDateTo;
    }
    public String getuId() {
        return uId;
    }
    public void setuId(String uId) {
        this.uId = uId;
    }
    public Integer getRgId() {
        return rgId;
    }
    public void setRgId(Integer rgId) {
        this.rgId = rgId;
    }
    public Date getRvDateFrom() {
        return rvDateFrom;
    }
    public void setRvDateFrom(Date rvDateFrom) {
        this.rvDateFrom = rvDateFrom;
    }
    public Date getRvDateTo() {
        return rvDateTo;
    }
    public void setRvDateTo(Date rvDateTo) {
        this.rvDateTo = rvDateTo;
    }

    
}
