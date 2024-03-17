package com.soez.ezcheck.room.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soez.ezcheck.room.dto.RoomSearchRequestDto;
import com.soez.ezcheck.room.entity.Room;
import com.soez.ezcheck.room.service.RoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final   RoomService roomService;

    @GetMapping("/search")
    public ResponseEntity<List<Room>> searchRoom(@RequestBody RoomSearchRequestDto requestDto){
        List<Room> rooms = roomService.findRoomByDateAndType(requestDto.getRsvCheckIn(), requestDto.getRsvCheckOut(), requestDto.getRoomType());
        return ResponseEntity.ok(rooms);
    }
}
