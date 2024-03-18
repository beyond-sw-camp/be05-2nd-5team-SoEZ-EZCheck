package com.soez.ezcheck.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soez.ezcheck.room.domain.Room;

import java.sql.Date;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("SELECT r FROM Room r WHERE r.roomType = :roomType AND r NOT IN (" +
           "SELECT res.room FROM Reservation res WHERE " +
           "res.rsvCheckIn <= :checkOutDate AND res.rsvCheckOut >= :checkInDate)")
    List<Room> findAvailableRoomsByDateAndType(@Param("checkInDate") Date checkInDate, @Param("checkOutDate")Date checkOutDate, @Param("roomType")String roomType);
}
