package com.soez.ezcheck.checkout.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soez.ezcheck.checkIn.repository.CheckInRepository;
import com.soez.ezcheck.checkout.domain.CheckOutDTO;
import com.soez.ezcheck.checkout.service.CheckOutServiceImpl;
import com.soez.ezcheck.entity.CheckOutStatusEnum;
import com.soez.ezcheck.entity.Room;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/checkouts")
@RequiredArgsConstructor
public class CheckOutController {

	private final CheckOutServiceImpl checkOutServiceImpl;
	private final CheckInRepository checkInRepository;

	/**
	 * 조건에 상관없이 모든 체크아웃 요청 내역을 최신순으로 조회
	 * @author Jihwan
	 * @return 최신순으로 정렬된 모든 체크아웃 요청들 List<>
	 */
	@GetMapping("/all")
	public ResponseEntity<List<CheckOutDTO>> getAllCheckOutRecords() {
		List<CheckOutDTO> checkOutRecords = checkOutServiceImpl.getAllCheckOutRecords();
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
		List<CheckOutDTO> checkOutRecords = checkOutServiceImpl.getCheckOutRecordsByDate(selectedDate);
		return new ResponseEntity<>(checkOutRecords, HttpStatus.OK);
	}

	/**
	 * 체크아웃 요청 승인
	 * @author Jihwan
	 * @param coutId 승인할 체크아웃 요청 ID
	 * @return 체크아웃 승인 메시지
	 */
	@GetMapping("/approve/{coutId}")
	public ResponseEntity<String> approveCheckOutRequest(@PathVariable Integer coutId) {
		checkOutServiceImpl.approveCheckOut(coutId);
		return new ResponseEntity<>("체크아웃 요청을 승인하였습니다.", HttpStatus.OK);
	}

	/**
	 * 체크아웃 요청 거절
	 * @author Jihwan
	 * @param coutId 거절할 체크아웃 요청 ID
	 * @return 체크아웃 거절 메시지
	 */
	@GetMapping("/reject/{coutId}")
	public ResponseEntity<String> rejectCheckOutRequest(@PathVariable Integer coutId) {
		checkOutServiceImpl.rejectCheckOut(coutId);
		return new ResponseEntity<>("체크아웃 요청을 거절하였습니다.", HttpStatus.OK);
	}

	/**
	 체크아웃 요청
	 @param rId 객실 ID
	 @return 체크아웃 요청(rId)
	 */
	@PostMapping("/checkoutrequest")
	public String requestCheckOut(@RequestBody Map<String, Integer> requestBody) { //roomid로 받고
		System.out.println("체크아웃 요청이 들어왔습니다. : " + requestBody);
		checkOutServiceImpl.requestCheckOut(requestBody.get("rId"));
		return "체크아웃이 요청되었습니다.";
	}

	/**
	 체크아웃 확정
	 */
	@PostMapping("/perform")
	public ResponseEntity<String> performCheckOut(@RequestParam(required = false) Integer cinId) {
    if (cinId == null || !checkInRepository.existsById(cinId)) {
        return ResponseEntity.ok("체크아웃되지 않았습니다.");
    } else {
        // 여기서 체크아웃 확정을 위한 로직 추가
        CheckOutDTO checkOutDTO = new CheckOutDTO();
        checkOutDTO.setCinId(cinId);
		checkOutDTO.setCheckOutStatusEnum(CheckOutStatusEnum.ACCEPTED);

        // CheckOut 서비스를 호출하여 체크아웃 확정 진행
        checkOutServiceImpl.checkOut(checkOutDTO.getCinId());

        return ResponseEntity.ok("체크아웃되었습니다.");
    }
}
}
