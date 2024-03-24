package com.soez.ezcheck.checkout.domain;

import java.sql.Date;
import java.sql.Time;

import com.soez.ezcheck.entity.CheckOutStatusEnum;

import lombok.Data;

@Data
public class CheckOutDTO {

	private Integer coutId;
	private CheckOutStatusEnum checkOutStatusEnum;
	private Date coutDate;
	private Time coutTime;
	private String uId;
	private Integer cinId;

}
