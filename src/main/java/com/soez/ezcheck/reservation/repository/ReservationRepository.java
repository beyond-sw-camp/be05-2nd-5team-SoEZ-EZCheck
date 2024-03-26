package com.soez.ezcheck.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soez.ezcheck.entity.Reservation;
import com.soez.ezcheck.entity.Users;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer>{
    List<Reservation> findByUsers(Users users);
}
