package com.soez.ezcheck.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soez.ezcheck.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>{
    
    
}
