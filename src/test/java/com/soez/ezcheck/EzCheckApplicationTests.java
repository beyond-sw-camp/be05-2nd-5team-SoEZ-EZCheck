package com.soez.ezcheck;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.soez.ezcheck.reservation.service.ReservationService;
import com.soez.ezcheck.room.service.RoomService;

@SpringBootTest
class EzCheckApplicationTests {

@Autowired
private ReservationService reservationService;

@Autowired
private RoomService roomService;

    @Test
    void reservationFindAll() {
        reservationService.findAll();
    }

    @Test
    void roomServiceFindByDateAndType(){
        LocalDate customDate1 = LocalDate.of(2024, 3, 20);
        LocalDate customDate2 = LocalDate.of(2024, 3, 20);

        roomService.findRoomByDateAndType(Date.valueOf(customDate1), Date.valueOf(customDate2), "standard");
    }
}
