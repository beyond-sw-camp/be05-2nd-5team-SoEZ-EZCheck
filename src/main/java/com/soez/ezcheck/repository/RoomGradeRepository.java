package com.soez.ezcheck.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soez.ezcheck.entity.RoomGrade;

public interface RoomGradeRepository extends JpaRepository<RoomGrade, Integer>{
    @Query("SELECT rg FROM RoomGrade rg WHERE (SELECT COUNT(r) FROM Room r WHERE r.roomGrade = rg) > " +
           "(SELECT COUNT(res) FROM Reservation res WHERE res.roomGrade = rg AND " +
           "(res.rvDateFrom <= :checkOutDate AND res.rvDateTo >= :checkInDate))")
    List<RoomGrade> findRoomGradesWithAvailability(@Param("checkInDate") Date checkInDate, 
                                                   @Param("checkOutDate") Date checkOutDate);
}
