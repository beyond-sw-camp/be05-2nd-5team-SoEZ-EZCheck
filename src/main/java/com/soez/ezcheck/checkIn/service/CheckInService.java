package com.soez.ezcheck.checkIn.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.soez.ezcheck.checkIn.repository.CheckInRepository;
import com.soez.ezcheck.entity.CheckIn;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CheckInService {
	private final CheckInRepository checkInRepository;

	public List<CheckIn> checkInsByDate(Date date) {

		return checkInRepository.findByCinDate(date);
	}

}