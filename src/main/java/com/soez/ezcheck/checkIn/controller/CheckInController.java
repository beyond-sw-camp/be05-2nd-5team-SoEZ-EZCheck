package com.soez.ezcheck.checkIn.controller;

import java.sql.Date;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soez.ezcheck.checkIn.domain.CheckInRequestDTO;
import com.soez.ezcheck.checkIn.repository.CheckInRepository;
import com.soez.ezcheck.checkIn.service.CheckInService;
import com.soez.ezcheck.entity.CheckIn;
import com.soez.ezcheck.entity.Reservation;
import com.soez.ezcheck.entity.Room;
import com.soez.ezcheck.reservation.repository.ReservationRepository;
import com.soez.ezcheck.reservation.service.ReservationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/checkIn")
@RequiredArgsConstructor
public class CheckInController {

	private final CheckInService checkInService;
	private final ReservationRepository reservationRepository;
	private final CheckInRepository checkInRepository;
	private final ReservationService reservationService;


	@Tag(name = " checkIn-User()")
	@GetMapping("/checkInUsers/{date}")
	public List<CheckIn> getMethodName(@PathVariable("date") Date date) {
		List<CheckIn> list = checkInService.checkInsByDate(date);

		return list;
	}

	@PreAuthorize("hasAuthority('User')")
	@GetMapping("/reservations/{uId}")
	public ResponseEntity<List<Reservation>> findAllReservationsById(@PathVariable("rvId") Integer rvId) {
		List<Reservation> reservations = checkInService.findAllReservationsById(rvId);
		return ResponseEntity.ok(reservations);
	}

	@PreAuthorize("hasAuthority('User')")
	@GetMapping("/reservations/available")
	public ResponseEntity<List<Room>> findAvailableRooms(@RequestParam Integer rId) {
		List<Room> availableRooms = checkInService.findAvailableRooms(rId);
		return ResponseEntity.ok(availableRooms);
	}

	@PreAuthorize("hasAuthority('User')")
	@PostMapping("/pcheckIn")
	public ResponseEntity<String> checkIn(@RequestBody CheckInRequestDTO request) {
		try {

			//List<Reservation> availableReservations = checkInService.findAllReservationsByuId(request.getUserId());
			List<Reservation> availableReservations = checkInService.findAllReservationsById(request.getRgId());
			// 체크인 로직 수행
			String checkInResult = checkInService.checkInReservation(availableReservations, request);
			return ResponseEntity.ok(checkInResult);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("체크인 요청 처리 중 오류가 발생했습니다. " + e.getMessage());
		}
	}

}
