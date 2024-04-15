package com.soez.ezcheck.checkIn.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckInRequestDTO {

    private String userId;
    private Integer rvId;
    private String roomPwd;
    private Integer roomId;
    private Integer rgId;

}
