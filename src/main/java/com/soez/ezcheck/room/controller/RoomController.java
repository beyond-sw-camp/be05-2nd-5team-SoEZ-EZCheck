package com.soez.ezcheck.room.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {
    
    private final RoomService roomService;

    @GetMapping("/availableRooms")
    public ResponseEntity<List<Room>> getMethodName(@RequestBody AvailableRoomsRequestDTO requestParam) {
        ResponseEntity<List<Object[]>> responseEntity = null;
        
        if(requestParam.getRoomGradeEnum() == null){
            System.out.println("debug >>>> don't have room grade");
            //responseEntity = roomService.findReservableRoomsByDate(requestParam.getRvDateFrom(), requestParam.getRvDateTo());
        }  else {
            System.out.println("debug >>>> have room grade");
            //responseEntity = roomService.findReservableRoomsByDateAndType(requestParam.getRvDateFrom(), requestParam.getRvDateTo(), requestParam.getRoomGradeEnum());
        }

        System.out.println(responseEntity.getBody());
        return null;
    }



    // 청소가 끝난 방의 ID 알고 있음
    // 그 방의 객실 상태를 AVAILABLE 로 변경
    // 쿼리 실행 메서드를 조건에 부합하는 결과물
    @GetMapping(value = "/updateRoomStatus/{rid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> updateRoomStatus(@PathVariable("rid") Integer roomId) {
        Optional<Room> room = roomService.find(roomId);
        String msg;
        if (room.isPresent()) {
            msg = roomService.updateRoomStatus(room.get().getRId());
        } else {
            msg = "객실 상태 변경에 실패했습니다. 객실 번호를 다시 확인해 주세요.";
        }
        room.ifPresent(value -> System.out.println("debug >>>>>>>>> " + value));

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping("/allRooms")
    public List<Room> getAllRooms() {
        List<Room> rooms = roomService.findAll();
        return rooms ;
    }
    
    
}
