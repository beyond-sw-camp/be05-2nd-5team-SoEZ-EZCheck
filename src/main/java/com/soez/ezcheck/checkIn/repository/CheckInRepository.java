package com.soez.ezcheck.checkIn.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soez.ezcheck.entity.CheckIn;
import com.soez.ezcheck.entity.Reservation;
import com.soez.ezcheck.entity.Room;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
	List<CheckIn> findByCinDate(Date cinDate);

	CheckIn findByReservation(Reservation reservation);

	CheckIn findByRoom(Room room);

	@Modifying
	@Query("DELETE FROM CheckIn ci WHERE ci.reservation.rvId = :reservationId")
	void deleteByReservationId(@Param("reservationId") Integer reservationId);

}