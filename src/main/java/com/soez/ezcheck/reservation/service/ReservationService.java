package com.soez.ezcheck.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.soez.ezcheck.entity.Reservation;
import com.soez.ezcheck.reservation.domain.ReservationRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
public interface ReservationService {
    List<Reservation> findAll();

    void addReservation(ReservationRequestDTO param);

    boolean deleteReservation(Reservation param);
}
