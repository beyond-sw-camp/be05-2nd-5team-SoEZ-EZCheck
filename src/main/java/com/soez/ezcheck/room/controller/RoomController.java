package com.soez.ezcheck.room.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soez.ezcheck.entity.Reservation;
import com.soez.ezcheck.entity.Room;
import com.soez.ezcheck.room.domain.AvailableRoomsRequestDTO;
import com.soez.ezcheck.room.service.RoomService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {
    
    private final RoomService roomService;

    @GetMapping("/availableRooms")
    public ResponseEntity<List<Room>> getMethodName(@RequestBody AvailableRoomsRequestDTO requestParam) {
        ResponseEntity<List<Room>> responseEntity = null;
        
        if(requestParam.getRoomGradeEnum() == null){
            System.out.println("debug >>>> don't have room grade");
            responseEntity = roomService.findReservableRoomsByDate(requestParam.getRvDateFrom(), requestParam.getRvDateTo());
        }  else {
            System.out.println("debug >>>> have room grade");
            responseEntity = roomService.findReservableRoomsByDateAndType(requestParam.getRvDateFrom(), requestParam.getRvDateTo(), requestParam.getRoomGradeEnum());
        }
        return null;
    }
    
}
