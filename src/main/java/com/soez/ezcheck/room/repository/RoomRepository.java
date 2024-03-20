package com.soez.ezcheck.room.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soez.ezcheck.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer>{
    
    @Query("SELECT r FROM Room r WHERE r.roomStatusEnum = com.soez.ezcheck.entity.RoomStatusEnum.AVAILABLE AND r NOT IN " +
           "(SELECT res.roomGrade.room FROM Reservation res WHERE (res.rvDateFrom <= :checkOutDate AND res.rvDateTo >= :checkInDate))")
    List<Room> findAvailableRoomsByDate(@Param("checkInDate") Date checkInDate, @Param("checkOutDate") Date checkOutDate);
}
