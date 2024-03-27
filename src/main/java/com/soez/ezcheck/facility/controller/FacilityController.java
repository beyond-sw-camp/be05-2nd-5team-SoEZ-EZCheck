package com.soez.ezcheck.facility.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soez.ezcheck.entity.Facility;
import com.soez.ezcheck.facility.domain.FacilityReservationDetailsDTO;
import com.soez.ezcheck.facility.domain.FacilityReservationRequestDTO;
import com.soez.ezcheck.facility.service.FacilityServiceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/facility")
@RequiredArgsConstructor
public class FacilityController {

	private final FacilityServiceImpl facilityService;

	/**
	 * 예약하려는 인원수, 날짜, 시간을 고려해서 예약가능한 시설물을 조회하는 기능
	 * @author Jihwan
	 * @param requestDTO 예약을 원하는 인원수, 날짜, 시간
	 * @return 조회된 예약이 가능한 시설물들 리스트 List<>
	 */
	 @GetMapping("/list")
	public ResponseEntity<List<Facility>> listFacilities(@RequestBody FacilityReservationRequestDTO requestDTO) {
		Integer peopleToReserve = requestDTO.getPeopleToReserve();
		Date date = requestDTO.getDate();
		Time time = requestDTO.getTime();

		// 예약하려는 인원수를 수용가능한 시설물 조회
		List<Facility> availableFacilities = facilityService.findAvailableFacilities(peopleToReserve);

		if (availableFacilities.isEmpty()) {
			return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
		}

		// 인원수에 더해서 날짜와 시간까지 고려했을 때에도 예약이 가능한 시설물들 조회
		List<Facility> reservableFacilities = facilityService.findReservableFacilities(availableFacilities,
			peopleToReserve, date, time);
		// 조회된 예약이 가능한 시설물 목록을 사용자에게 반환
		return new ResponseEntity<>(reservableFacilities, HttpStatus.OK);
	}

	/**
	 * 시설물을 특정해서 예약 레코드를 생성하는 메서드
	 * @author Jihwan
	 * @param requestDTO 시설물 ID, 사용자 ID, 예약할 날짜, 예약할 시간, 예약할 인원수
	 * @return 날짜와 시간을 포함한 예약 완료 메시지
	 */
	@PreAuthorize("hasAuthority('User')")
	@PostMapping("/reserve")
	public ResponseEntity<String> makeReservation(@RequestBody FacilityReservationRequestDTO requestDTO) {
		String msg = facilityService.makeReservation(requestDTO);
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}

	/**
	 * 사용자 ID로 생성되어 있는 예약 내역들을 조회
	 * @author Jihwan
	 * @param uId 사용자 ID
	 * @return 예약내역(시설명, 예약 날짜, 예약 시간, 예약 인원수)들을 담은 List<>
	 */
	@PreAuthorize("hasAuthority('User')")
	 @GetMapping("/user/{uId}")
	public List<FacilityReservationDetailsDTO> getReservationDetails(@PathVariable("uId") String uId) {
		return facilityService.getReservationDetails(uId);
	}

	@PreAuthorize("hasAuthority('Admin')")
	@PutMapping("/open/{facilityid}")
	public ResponseEntity<String> openFacility(@PathVariable("facilityid") Integer facilityId) {
		try {
			facilityService.openFacility(facilityId);
			return ResponseEntity.ok("Facility opened successfully.");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	// 시설물 클로우즈   설정4\
	@PreAuthorize("hasAuthority('Admin')")
	@PutMapping("/close/{facilityid}")
	public ResponseEntity<String> closeFacility(@PathVariable("facilityid") Integer facilityId) {
		try {
			facilityService.closeFacility(facilityId);
			return ResponseEntity.ok("Facility closed successfully.");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	// 시간 별 시설물 조회 시설물 id
	@PreAuthorize("hasAuthority('Admin')")
	@GetMapping("/facilityreservation/{date}/{time}/{reservNum}")
	public List<Facility> getAvailabilityForFacility(@PathVariable("date") Date date,
		@PathVariable("time") Time time, @PathVariable("reservNum") Integer num) {
		return facilityService.getAvailableFacility(date, time, num);
	}

}
