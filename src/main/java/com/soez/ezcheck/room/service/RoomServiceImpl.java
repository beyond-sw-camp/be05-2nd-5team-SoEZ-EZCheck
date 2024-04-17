package com.soez.ezcheck.room.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.soez.ezcheck.entity.RoomStatusEnum;
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


    @Override
    public Optional<Room> find(Integer roomId) {
        return roomRepository.findById(roomId);
    }

    @Override
    public String updateRoomStatus(Integer roomId) {
        Optional<Room> data = roomRepository.findById(roomId); // 방 번호를 기준으로 방을 찾음
        if (data.isPresent()) {
            // 새로운 상태를 설정하거나, 상태를 업데이트하는 방법에 따라 변경 가능
            // 여기서는 임의로 "AVAILABLE"로 설정함
            data.get().setRoomStatusEnum(RoomStatusEnum.AVAILABLE);
            Room updatedRoom = roomRepository.save(data.get()); // 변경된 상태를 저장하고 업데이트된 방을 받음
            return "객실 상태를 변경했습니다"; // 업데이트된 방 정보를 리스트에 담아 반환함
        }
        return "객실 상태 변경에 실패했습니다. 객실 번호를 다시 확인해 주세요.";
    }


    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }
    
}
