package com.soez.ezcheck.room.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.soez.ezcheck.entity.Room;
import com.soez.ezcheck.entity.RoomGradeEnum;

@Service
public interface RoomService {
    //ResponseEntity<List<Object[]>> findReservableRoomsByDate(Date checkin, Date checkout);

    ResponseEntity<List<Object[]>> findReservableRoomsByDateAndType(Date checkin, Date checkout, RoomGradeEnum type);

    List<Room> findAll();


    public Optional<Room> find(Integer roomId);
    public String updateRoomStatus(Integer roomId);


}
