package com.soez.ezcheck.reservation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soez.ezcheck.entity.Reservation;
import com.soez.ezcheck.reservation.domain.ReservationRequestDTO;
import com.soez.ezcheck.reservation.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{
    
    private final ReservationRepository reservationRepository;

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public void addReservation(ReservationRequestDTO requestDTO) {

        Reservation reservation = new Reservation();
        /*
        // 사용자 정보 가져오기
        Optional<User> userOptional = userRepository.findById(requestDTO.getUId());
        if (userOptional.isPresent()) {
            reservation.setUser(userOptional.get());
        } else {
            // 사용자를 찾을 수 없는 경우 예외 처리
            throw new IllegalArgumentException("User not found");
        }
        
        // 방 등급 정보 가져오기
        Optional<RoomGrade> roomGradeOptional = roomGradeRepository.findById(requestDTO.getRgId());
        if (roomGradeOptional.isPresent()) {
            reservation.setRoomGrade(roomGradeOptional.get());
        } else {
            // 방 등급을 찾을 수 없는 경우 예외 처리
            throw new IllegalArgumentException("RoomGrade not found");
        }

        // 날짜 설정
        reservation.setRvDateFrom(requestDTO.getRvDateFrom());
        reservation.setRvDateTo(requestDTO.getRvDateTo());
         */

        reservationRepository.save(reservation);
    }

    public boolean deleteReservation(Reservation reservation) {
        try {
            reservationRepository.delete(reservation);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    
}
