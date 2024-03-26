package com.soez.ezcheck.reservation.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.soez.ezcheck.entity.Reservation;
import com.soez.ezcheck.entity.RoomGrade;
import com.soez.ezcheck.entity.Users;
import com.soez.ezcheck.repository.RoomGradeRepository;
import com.soez.ezcheck.reservation.domain.ReservationRequestDTO;
import com.soez.ezcheck.reservation.repository.ReservationRepository;
import com.soez.ezcheck.user.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

	private final ReservationRepository reservationRepository;
	private final UsersRepository userRepository;
	private final RoomGradeRepository roomGradeRepository;

	@Override
	public List<Reservation> findAll() {
		return reservationRepository.findAll();
	}

	@Override
	public boolean addReservation(ReservationRequestDTO requestDTO) {

		Reservation reservation = new Reservation();

		Optional<Users> userOptional = userRepository.findById(requestDTO.getuId());
		if (userOptional.isPresent()) {
			reservation.setUsers(userOptional.get());
		} else {
			throw new IllegalArgumentException("User not found");
		}
		Optional<RoomGrade> roomGradeOptional = roomGradeRepository.findById(requestDTO.getRgId());
		if (roomGradeOptional.isPresent()) {
			reservation.setRoomGrade(roomGradeOptional.get());
		} else {
			throw new IllegalArgumentException("RoomGrade not found");
		}
		reservation.setRvDateFrom(requestDTO.getRvDateFrom());
		reservation.setRvDateTo(requestDTO.getRvDateTo());
		try {
			reservationRepository.save(reservation);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Reservation> findMyReservations(String uId) {
		Optional<Users> userOptional = userRepository.findById(uId);
		if (userOptional.isPresent()) {
			return reservationRepository.findByUsers(userOptional.get());
		} else {
			throw new IllegalArgumentException("User not found");
		}
	}

	@Override
	public boolean deleteReservation(Reservation reservation) {
		try {
			reservationRepository.delete(reservation);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<RoomGrade> avalableRoomGrades(Date checkInDate, Date checkOutDate) {
		List<RoomGrade> roomGrades = roomGradeRepository.findRoomGradesWithAvailability(checkInDate, checkOutDate);
		if (roomGrades.isEmpty()) {
			throw new IllegalArgumentException("No available room grades");
		} else {
			return roomGrades;
		}
	}

}
