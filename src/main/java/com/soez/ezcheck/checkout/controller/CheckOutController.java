package com.soez.ezcheck.checkout.controller;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soez.ezcheck.checkout.domain.CheckOutDTO;
import com.soez.ezcheck.checkout.service.CheckOutService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/checkouts")
@RequiredArgsConstructor
public class CheckOutController {

	private final CheckOutService checkOutService;

	/**
	 * 조건에 상관없이 모든 체크아웃 요청 내역을 최신순으로 조회
	 * @author Jihwan
	 * @return 최신순으로 정렬된 체크아웃 요청들 List<>
	 */
	@GetMapping("/all")
	public ResponseEntity<List<CheckOutDTO>> getAllCheckOutRecords() {
		List<CheckOutDTO> checkOutRecords = checkOutService.getAllCheckOutRecords();
		return new ResponseEntity<>(checkOutRecords, HttpStatus.OK);
	}

	/**
	 * 날짜를 기반으로 체크아웃 요청 내역을 최신순으로 조회
	 * @author Jihwan
	 * @param selectedDate 조회하려는 날짜
	 * @return 최신순으로 정렬된 체크아웃 요청들 List<>
	 */
	@GetMapping("/date")
	public ResponseEntity<List<CheckOutDTO>> getCheckOutRecordsByDate(
		@RequestParam("selectedDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date selectedDate) {
		List<CheckOutDTO> checkOutRecords = checkOutService.getCheckOutRecordsByDate(selectedDate);
		return new ResponseEntity<>(checkOutRecords, HttpStatus.OK);
	}

}
