package com.soez.ezcheck.reservation.service;

import org.springframework.stereotype.Service;

import com.soez.ezcheck.reservation.entity.Reservation;
import com.soez.ezcheck.reservation.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<Reservation> findAll(){
        return reservationRepository.findAll();
    }
}
