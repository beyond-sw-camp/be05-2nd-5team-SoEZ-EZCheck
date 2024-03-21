package com.soez.ezcheck;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.soez.ezcheck.reservation.service.ReservationService;
import com.soez.ezcheck.reservation.service.ReservationServiceImpl;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
class EzCheckApplicationTests {

    private final ReservationServiceImpl reservationService;

    @Test
    void contextLoads() {
        reservationService.findAll();
    }

}
