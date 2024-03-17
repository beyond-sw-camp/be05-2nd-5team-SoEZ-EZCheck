package com.soez.ezcheck.room.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.soez.ezcheck.room.entity.Room;
import com.soez.ezcheck.room.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public List<Room> findRoomByDateAndType(Date checkInDate, Date checkOutDate, String roomType){
        return roomRepository.findAvailableRoomsByDateAndType(checkInDate, checkOutDate, roomType);
    }
}
