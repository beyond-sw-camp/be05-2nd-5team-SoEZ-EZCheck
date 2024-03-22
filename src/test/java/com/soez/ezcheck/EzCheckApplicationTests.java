package com.soez.ezcheck;

import com.soez.ezcheck.reservation.service.ReservationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor
class EzCheckApplicationTests {

    private final ReservationServiceImpl reservationService;

    @Test
    void contextLoads() {
        reservationService.findAll();
    }

}
