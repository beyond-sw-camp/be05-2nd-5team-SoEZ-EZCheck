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
    /*
    public void updateRoomStatus(Integer roomId, RoomStatusEnum roomStatus);
    */
}
