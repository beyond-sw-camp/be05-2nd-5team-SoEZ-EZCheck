package com.soez.ezcheck.checkout.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soez.ezcheck.checkout.domain.CheckOutDTO;
import com.soez.ezcheck.checkout.repository.CheckOutRepository;
import com.soez.ezcheck.entity.CheckOut;
import com.soez.ezcheck.entity.CheckOutStatusEnum;
import com.soez.ezcheck.entity.Room;
import com.soez.ezcheck.entity.RoomStatusEnum;
import com.soez.ezcheck.room.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckOutServiceImpl {

	private final CheckOutRepository checkOutRepository;
	private final RoomRepository roomRepository;

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
	 * 체크아웃 ID로 참조된 체크아웃 요청을 승인 및 해당 객실의 상태를 AVAILABLE로 변경
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

}
