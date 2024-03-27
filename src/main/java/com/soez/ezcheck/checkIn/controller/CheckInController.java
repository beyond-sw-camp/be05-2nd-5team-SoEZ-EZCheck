package com.soez.ezcheck.checkIn.controller;

import com.soez.ezcheck.checkIn.domain.CheckInRequestDTO;
import com.soez.ezcheck.checkIn.repository.CheckInRepository;
import com.soez.ezcheck.checkIn.service.CheckInService;
import com.soez.ezcheck.entity.Reservation;
import com.soez.ezcheck.entity.Room;
import com.soez.ezcheck.reservation.repository.ReservationRepository;
import com.soez.ezcheck.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkIn")
@RequiredArgsConstructor
public class CheckInController {

    private final CheckInService checkInService;

    private final ReservationRepository reservationRepository;

    private final CheckInRepository checkInRepository;

    private final ReservationService reservationService;

    // 사용자 ID에 따른 모든 예약 객실 정보 조회
    /*
    @GetMapping("/reservations/{uId}")
    public ResponseEntity<List<Reservation>> findAllReservationsByUserId(@PathVariable("uId") String uId) {
        List<Reservation> reservations = checkInService.findAllReservationsByuId(uId);
        return ResponseEntity.ok(reservations);
    }
    */
    @GetMapping("/reservations/{uId}")
    public ResponseEntity<List<Reservation>> findAllReservationsById(@PathVariable("rvId") Integer rvId) {
        List<Reservation> reservations = checkInService.findAllReservationsById(rvId);
        return ResponseEntity.ok(reservations);
    }

    // 체크인 가능한 객실 조회
   /*
    @GetMapping("/reservations/available")
    public ResponseEntity<List<Room>> findAvailableRooms(@RequestBody List<RoomGrade> roomGrades) {
        List<Room> availableRooms = checkInService.findAvailableRooms(roomGrades);
        return ResponseEntity.ok(availableRooms);
    }
    */
    @GetMapping("/reservations/available")
    public ResponseEntity<List<Room>> findAvailableRooms(@RequestParam Integer rId) {
        List<Room> availableRooms = checkInService.findAvailableRooms(rId);
        return ResponseEntity.ok(availableRooms);
    }

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
