package com.soez.ezcheck.checkIn.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soez.ezcheck.checkIn.service.CheckInService;
import com.soez.ezcheck.entity.CheckIn;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/checkIn")
@RequiredArgsConstructor
public class CheckInController {
	private final CheckInService checkInService;

	@GetMapping("/checkInUsers/{date}")
	public List<CheckIn> getMethodName(@PathVariable("date") Date date) {
		List<CheckIn> list = checkInService.checkInsByDate(date);

		return list;
	}

}