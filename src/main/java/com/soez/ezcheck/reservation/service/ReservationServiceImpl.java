package com.soez.ezcheck.reservation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soez.ezcheck.entity.Reservation;
import com.soez.ezcheck.reservation.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{
    
    private final ReservationRepository reservationRepository;

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    
    
}
