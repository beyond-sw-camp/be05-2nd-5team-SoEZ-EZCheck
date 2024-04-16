package com.soez.ezcheck.checkIn.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.soez.ezcheck.checkIn.domain.CheckInRequestDTO;
import com.soez.ezcheck.checkIn.repository.CheckInRepository;
import com.soez.ezcheck.entity.CheckIn;
import com.soez.ezcheck.entity.Reservation;
import com.soez.ezcheck.entity.Room;
import com.soez.ezcheck.entity.RoomStatusEnum;
import com.soez.ezcheck.reservation.repository.ReservationRepository;
import com.soez.ezcheck.room.repository.RoomRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CheckInService {
	private final CheckInRepository checkInRepository;

	private final RoomRepository roomRepository;

	private final ReservationRepository reservationRepository;

	public List<CheckIn> checkInsByDate(Date date) {

		return checkInRepository.findByCinDate(date);
	}

	@PersistenceContext
	private EntityManager entityManager;

	// 사용자 ID에 따른 모든 예약 객실 정보 조회 x
	// 예약 ID에 따른 모든 예약 객실 정보 조회
	public List<Reservation> findAllReservationsById(Integer rvId) {
		// 사용자 ID(String)에 따른 모든 예약 정보 조회 (수정 필요)

		//return reservationRepository.findByUsers_UId(uId);
		Iterable<Integer> iterable = Collections.singleton(rvId);
		return reservationRepository.findAllById(iterable);

	}

	// 체크인 가능한 객실 조회(각각의 객실 등급에 속한 객실들을 확인하고, 그 중에서 상태가 AVAILABLE인 객실들을 모두 모아서 리스트로 반환.)
	public List<Room> findAvailableRooms() {
		// 모든 객실을 가져와서 사용 가능한 객실만 필터링하여 리스트로 반환
		return roomRepository.findAll().stream()
			.filter(room -> room.getRoomStatusEnum() == RoomStatusEnum.AVAILABLE)
			.collect(Collectors.toList());
	}

	public List<Room> findAvailableRooms(Integer rId) {
		Optional<Room> roomData = roomRepository.findById(rId);
		if (roomData.isPresent()) {
			Room room = roomData.get();
			if (room.getRoomStatusEnum() == RoomStatusEnum.AVAILABLE) {
				return Collections.singletonList(room);
			}
		}
		return Collections.emptyList();
	}

    /*
       public String updateServiceRequestStatus(Integer rqId) {
        Optional<ServiceRequest> data = serviceRequestRepository.findById(rqId);
        if ( data.isPresent() ) {
            data.get().setServiceRequestStatusEnum(ServiceRequestStatusEnum.COMPLETED);
            ServiceRequest updateRequestStatus = serviceRequestRepository.save(data.get());
            return "객실 상태를 변경했습니다^^";
        }
        return "객실 상태 변경에 실패했습니다. 요청자를 확인해 주세요";
    }
     */

	public String checkInReservation(List<Reservation> availableReservations, CheckInRequestDTO request) {
		// 체크인할 예약 정보 찾기
		Optional<Reservation> reservationOptional = reservationRepository.findById(request.getRvId());
		if (reservationOptional.isEmpty()) {
			throw new IllegalArgumentException("체크인할 예약 정보를 찾을 수 없습니다.");
		} else {

			Reservation reservationToCheckIn = reservationOptional.get();
			Optional<Room> roomOptional = roomRepository.findById(request.getRoomId());
			if (roomOptional.isPresent()) {
				Room room = roomOptional.get();
				if (room.getRoomStatusEnum() == RoomStatusEnum.AVAILABLE) {
					List<Room> availableRooms = Collections.singletonList(room);

					// Select the first available room
					Room roomToCheckIn = availableRooms.get(0);

					// 객실 상태 및 비밀번호 업데이트
					roomToCheckIn.setRPwd(request.getRoomPwd());
					roomToCheckIn.setRoomStatusEnum(RoomStatusEnum.OCCUPIED);
					roomRepository.save(roomToCheckIn);

					// 체크인 정보 저장
					CheckIn checkIn = new CheckIn();
					LocalDateTime currentTime = LocalDateTime.now();
					checkIn.setCinDate(Date.valueOf(currentTime.toLocalDate()));
					checkIn.setCinTime(Time.valueOf(currentTime.toLocalTime()));
					checkIn.setReservation(reservationToCheckIn);
					checkIn.setRoom(room);
					checkInRepository.save(checkIn);

					return "체크인이 완료되었습니다.";
				}
			} else {
				throw new IllegalArgumentException("체크인 가능한 객실을 찾을 수 없습니다.");
			}

		}
		return null;
	}

	public CheckIn findByRvId(Integer rvId) {
		return checkInRepository.findByReservation(reservationRepository.findById(rvId).get());
	}
}
