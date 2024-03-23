package com.soez.ezcheck.room.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soez.ezcheck.entity.Room;
import com.soez.ezcheck.entity.RoomGrade;
import com.soez.ezcheck.entity.RoomGradeEnum;

public interface RoomRepository extends JpaRepository<Room, Integer>{
    
    @Query("SELECT rg FROM RoomGrade rg WHERE rg IN " +
       "(SELECT r.roomGrade FROM Room r WHERE r.roomStatusEnum = com.soez.ezcheck.entity.RoomStatusEnum.AVAILABLE " +
       "AND r NOT IN (SELECT res.roomGrade FROM Reservation res " +
       "WHERE (res.rvDateFrom <= :checkOutDate AND res.rvDateTo >= :checkInDate)))")
    List<RoomGrade> findAvailableRoomGrades(@Param("checkInDate") Date checkInDate, 
                                        @Param("checkOutDate") Date checkOutDate);
}
