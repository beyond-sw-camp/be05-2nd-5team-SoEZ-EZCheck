package com.soez.ezcheck.reservation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soez.ezcheck.entity.Reservation;
import com.soez.ezcheck.entity.RoomGrade;
import com.soez.ezcheck.reservation.domain.ReservationRequestDTO;
import com.soez.ezcheck.reservation.service.ReservationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

	private final ReservationService reservationService;

	@PostMapping("/listAvailableGrades")
	public ResponseEntity<List<RoomGrade>> listAvailableGrades(@RequestBody ReservationRequestDTO requestDTO) {
		List<RoomGrade> list = reservationService.avalableRoomGrades(requestDTO.getRvDateFrom(),
			requestDTO.getRvDateTo());
		if (list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		}
	}

	@PreAuthorize("hasAuthority('User')")
	@PostMapping("/make")
	public ResponseEntity<Boolean> makeReservation(@RequestBody ReservationRequestDTO requestDTO) {
		Boolean response = reservationService.addReservation(requestDTO);
		if (response) {
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PreAuthorize("hasAuthority('User')")
	@PostMapping("/myReservations")
	public ResponseEntity<List<Reservation>> myReservations(@RequestBody ReservationRequestDTO requestDTO) {
		List<Reservation> list = reservationService.findMyReservations(requestDTO.getuId());
		if (list.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		}
	}

	@PreAuthorize("hasAuthority('User')")
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteReservation(@RequestBody Reservation requestDTO) {
		boolean deleted = reservationService.deleteReservation(requestDTO);
		if (deleted) {
			return ResponseEntity.ok("Reservation deleted successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete reservation.");
		}
	}
}
