package com.soez.ezcheck;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.soez.ezcheck.reservation.entity.Reservation;
import com.soez.ezcheck.reservation.repository.ReservationRepository;
import com.soez.ezcheck.reservation.service.ReservationService;

import lombok.RequiredArgsConstructor;

@SpringBootTest
class EzCheckApplicationTests {

@Autowired
private ReservationService reservationService;

    @Test
    void reservationFindAll() {
        reservationService.findAll();
    }
}
