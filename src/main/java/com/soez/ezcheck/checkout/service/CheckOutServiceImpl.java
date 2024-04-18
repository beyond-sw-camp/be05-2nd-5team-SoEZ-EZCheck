package com.soez.ezcheck.checkout.service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.soez.ezcheck.checkIn.repository.CheckInRepository;
import com.soez.ezcheck.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soez.ezcheck.checkout.domain.CheckOutDTO;
import com.soez.ezcheck.checkout.repository.CheckOutRepository;
import com.soez.ezcheck.room.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckOutServiceImpl {

	private final CheckOutRepository checkOutRepository;
	private final RoomRepository roomRepository;
	private final CheckInRepository checkInRepository;

	/**
	 * 조건에 상관없이 모든 체크아웃 요청 내역을 최신순으로 조회
	 * @author Jihwan
	 * @return 최신순으로 정렬된 체크아웃 요청들 List<>
	 */
	@Transactional(readOnly = true)
	public List<CheckOutDTO> getAllCheckOutRecords() {
		List<CheckOut> checkOuts = checkOutRepository.getAllCheckOutRecords();
		return checkOuts.stream().map(this::mapToCheckOutDTO).collect(Collectors.toList());
	}

	/**
	 * 날짜를 기반으로 체크아웃 요청 내역을 최신순으로 조회
	 * @author Jihwan
	 * @param selectedDate 조회하려는 날짜
	 * @return 최신순으로 정렬된 체크아웃 요청들 List<>
	 */
	@Transactional(readOnly = true)
	public List<CheckOutDTO> getCheckOutRecordsByDate(Date selectedDate) {
		List<CheckOut> checkOuts = checkOutRepository.getCheckOutRecordsByDate(selectedDate);
		return checkOuts.stream().map(this::mapToCheckOutDTO).collect(Collectors.toList());
	}

	/**
	 * 체크아웃 엔터티를 DTO로 매핑시켜서 반환
	 * @author Jihwan
	 * @param checkOut DTO로 매핑시킬 체크아웃 엔터티
	 * @return DTO로 변환된 체크아웃 요청 내역
	 */
	public CheckOutDTO mapToCheckOutDTO(CheckOut checkOut) {
		CheckOutDTO dto = new CheckOutDTO();
		dto.setCoutId(checkOut.getCoutId());
		dto.setCheckOutStatusEnum(checkOut.getCheckOutStatusEnum());
		dto.setCoutDate(checkOut.getCoutDate());
		dto.setCoutTime(checkOut.getCoutTime());
		dto.setUId(checkOut.getUsers().getUId());
		dto.setCinId(checkOut.getCheckIn().getCinId());
		return dto;
	}

	/**
	 * 체크아웃 ID로 참조된 체크아웃 요청을 승인 및 해당 객실의 상태를 MAINTENANCE로 변경
	 * @author Jihwan
	 * @param checkoutId 승인할 체크아웃 요청 ID
	 */
	@Transactional
	public void approveCheckOut(Integer checkoutId) {
		CheckOut checkOut = checkOutRepository.findById(checkoutId)
			.orElseThrow(() -> new IllegalArgumentException(checkoutId + "번에 해당하는 체크아웃 요청을 찾을 수 없습니다"));

		checkOut.setCheckOutStatusEnum(CheckOutStatusEnum.ACCEPTED);
		checkOutRepository.save(checkOut);

		Room room = checkOut.getCheckIn().getRoom();
		room.setRoomStatusEnum(RoomStatusEnum.MAINTENANCE);
		roomRepository.save(room);
	}

	/**
	 * 체크아웃 ID로 참조된 체크아웃 요청을 거절
	 * @author Jihwan
	 * @param checkoutId 거절할 체크아웃 요청 ID
	 */
	@Transactional
	public void rejectCheckOut(Integer checkoutId) {
		CheckOut checkOut = checkOutRepository.findById(checkoutId)
			.orElseThrow(() -> new IllegalArgumentException(checkoutId + "번에 해당하는 체크아웃 요청을 찾을 수 없습니다"));

		checkOut.setCheckOutStatusEnum(CheckOutStatusEnum.REJECTED);
		checkOutRepository.save(checkOut);
	}

	public void requestCheckOut(Integer rId) {
		Optional<Room> room = roomRepository.findById(rId);
		CheckIn checkIn = checkInRepository.findByRoom(room.get());
				//checkInRepository.findById(rId)
				//.orElseThrow(() -> new IllegalArgumentException("체크인 정보를 찾을 수 없습니다."));

		// 이미 체크아웃 요청이 되었는지 확인
		Optional<CheckOut> existingCheckOut = checkOutRepository.findByCheckIn(checkIn);
		if (existingCheckOut.isPresent()) {
			throw new IllegalStateException("이미 체크아웃 요청이 처리되었거나 완료되었습니다.");
		}
		//체크아웃 요청
		CheckOut checkOut = new CheckOut();
		LocalDateTime currentTime = LocalDateTime.now();
		checkOut.setCheckIn(checkIn);
		checkOut.setCoutDate(java.sql.Date.valueOf(currentTime.toLocalDate()));
		checkOut.setCoutTime(Time.valueOf(currentTime.toLocalTime()));
		checkOut.setCheckOutStatusEnum(CheckOutStatusEnum.INPROGRESS); // 체크아웃 요청 상태로 설정
		checkOutRepository.save(checkOut);
	}


	// 관리자가 객실 상태를 변경하면 체크아웃 진행-> 객실 비밀번호 초기화, 체크인 정보 삭제
	public void checkOut(Integer cinId) {
		System.out.println("debug >>> CO service");
		CheckIn checkIn = checkInRepository.findById(cinId).orElseThrow(() -> new IllegalArgumentException("체크인 아이디를 찾을 수 없습니다."));

		Room room = checkIn.getRoom();
		// 관리자가 객실 상태를  AVAILABLE로 변경했는지 확인
		if (room.getRoomStatusEnum() != RoomStatusEnum.AVAILABLE) {
			throw new IllegalStateException("관리자가 아직 체크아웃을 승인하지 않았습니다.");
		} else {

			// 객실 비밀번호 초기화
			room.setRPwd("0000");//스트링으로 0000
			roomRepository.save(room);

			CheckOut checkOut = new CheckOut();
			LocalDateTime currentTime = LocalDateTime.now();
			checkOut.setCoutDate(java.sql.Date.valueOf(currentTime.toLocalDate()));
			checkOut.setCoutTime(Time.valueOf(currentTime.toLocalTime()));
			checkOutRepository.save(checkOut);

			// 체크인 정보 삭제
			checkInRepository.delete(checkIn);
		}
	}




}
