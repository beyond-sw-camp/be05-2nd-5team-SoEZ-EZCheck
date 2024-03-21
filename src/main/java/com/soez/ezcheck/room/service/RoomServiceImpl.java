package com.soez.ezcheck.room.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.soez.ezcheck.entity.Room;
import com.soez.ezcheck.entity.RoomGrade;
import com.soez.ezcheck.entity.RoomGradeEnum;
import com.soez.ezcheck.room.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService{
        private final RoomRepository roomRepository;


    @Override
    public ResponseEntity<List<Object []>> findReservableRoomsByDateAndType(Date checkin, Date checkout, RoomGradeEnum type) {

        return null;
    }
    
}
