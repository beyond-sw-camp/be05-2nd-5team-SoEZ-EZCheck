package com.soez.ezcheck.room.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soez.ezcheck.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer>{
    

}