package com.soez.ezcheck.reservation.service;

import java.sql.Date;
import java.util.List;
import org.springframework.stereotype.Service;

import com.soez.ezcheck.entity.Reservation;
import com.soez.ezcheck.entity.RoomGrade;
import com.soez.ezcheck.reservation.domain.ReservationRequestDTO;

@Service
public interface ReservationService {
    List<Reservation> findAll();

    List<RoomGrade> avalableRoomGrades(Date checkInDate, Date checkOutDate);

    boolean addReservation(ReservationRequestDTO param);

    List<Reservation> findMyReservations(String uId);
    
    boolean deleteReservation(Reservation param);
}
