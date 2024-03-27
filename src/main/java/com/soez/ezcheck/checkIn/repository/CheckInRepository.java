package com.soez.ezcheck.checkIn.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soez.ezcheck.entity.CheckIn;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
	List<CheckIn> findByCinDate(Date cinDate);
}