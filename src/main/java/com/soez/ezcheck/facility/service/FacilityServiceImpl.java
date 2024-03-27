package com.soez.ezcheck.facility.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soez.ezcheck.entity.Facility;
import com.soez.ezcheck.entity.FacilityReservation;
import com.soez.ezcheck.entity.FacilityStatusEnum;
import com.soez.ezcheck.entity.Users;
import com.soez.ezcheck.facility.domain.FacilityReservationDetailsDTO;
import com.soez.ezcheck.facility.domain.FacilityReservationRequestDTO;
import com.soez.ezcheck.facility.repository.FacilityRepository;
import com.soez.ezcheck.facility.repository.FacilityReservationRepository;
import com.soez.ezcheck.user.repository.UsersRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacilityServiceImpl {

	private final FacilityRepository facilityRepository;

	private final FacilityReservationRepository facilityReservationRepository;

	private final UsersRepository usersRepository;

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 예약을 원하는 인원수를 수용할 수 있는 시설물을 조회하는 기능
	 * @author Jihwan
	 * @param peopleToReserve 예약을 원하는 인원수
	 * @return 인원수를 수용하기에 충분한 시설물 List<>
	 */
	@Transactional(readOnly = true)
	public List<Facility> findAvailableFacilities(Integer peopleToReserve) {
		return facilityRepository.findAvailableFacilities(peopleToReserve, FacilityStatusEnum.OPEN);
	}

	/**
	 * 매개변수로 넘어오는 조건들에 부합하는 예약 가능한 시설물을 찾아서 반환하는 기능
	 * @author Jihwan
	 * @param availableFacilities 예약 인원수를 수용하기에 충분한 capacity를 갖고있는 시설물
	 * @param peopleToReserve 예약을 원하는 인원수
	 * @param date 예약을 원하는 날짜
	 * @param time 예약을 원하는 시간
	 * @return 예약 생성이 가능한 시설물 리스트 List<>
	 */
	@Transactional(readOnly = true)
	public List<Facility> findReservableFacilities(List<Facility> availableFacilities, Integer peopleToReserve,
		Date date, Time time) {
		List<Facility> reservableFacilities = new ArrayList<>();
		for (Facility facility : availableFacilities) {
			int currentReservationCount = getCurrentReservationCount(facility, date, time);
			if (currentReservationCount + peopleToReserve <= facility.getFCapacity()) {
				reservableFacilities.add(facility);
			}
		}
		return reservableFacilities;
	}

	/**
	 * 특정 시설물에 대한 예약내역을 특정 날짜와 시간으로 조회하여서, 동시간에 예약되어 있는 총 인원수를 계산
	 * @author Jihwan
	 * @param facility 조회하려는 시설물
	 * @param date 조회하려는 날짜
	 * @param time 조회하려는 시간
	 * @return 특정 시설물에 동시간에 예약되어 있는 인원수의 합
	 */
	public int getCurrentReservationCount(Facility facility, Date date, Time time) {
		List<FacilityReservation> reservations = facilityReservationRepository.findByFrDateAndFrTime(date, time);
		int count = 0;
		for (FacilityReservation reservation : reservations) {
			if (reservation.getFacility().getFId().equals(facility.getFId())) {
				count += reservation.getFrPeopleNum();
			}
		}
		return count;
	}

	/**
	 * 시설물을 특정해서 예약 레코드를 생성하는 메서드
	 * @author Jihwan
	 * @param requestDTO 시설물 ID, 사용자 ID, 예약할 날짜, 예약할 시간, 예약할 인원수
	 * @return 날짜와 시간을 포함한 예약 완료 메시지
	 */
	@Transactional
	public String makeReservation(FacilityReservationRequestDTO requestDTO) {
		// 수행할 SQL 구문
		String sql = "INSERT INTO facility_reservation (fr_date, fr_time, fr_people_num, f_id, u_id) " +
			"VALUES (:frDate, :frTime, :frPeopleNum, :fId, :uId)";

		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("frDate", requestDTO.getDate());
		query.setParameter("frTime", requestDTO.getTime());
		query.setParameter("frPeopleNum", requestDTO.getPeopleToReserve());
		query.setParameter("fId", requestDTO.getFacilityId());
		query.setParameter("uId", requestDTO.getUserId());

		// 쿼리 실행
		query.executeUpdate();

		return requestDTO.getDate().toString() + " " + requestDTO.getTime().toString() + " 에 예약이 완료되었습니다";
	}

	/**
	 * 시설예약 테이블로부터 사용자의 ID로 예약내역을 조회
	 * @author Jihwan
	 * @param uId 사용자 ID
	 * @return 예약 내역(시설명, 예약 날짜, 예약 시간, 예약 인원수) 리스트 List<>
	 */
	@Transactional(readOnly = true)
	public List<FacilityReservationDetailsDTO> getReservationDetails(String uId) {
		Users users = usersRepository.findById(uId).orElseThrow(()
			-> new IllegalArgumentException("존재하지 않는 회원입니다. 회원가입 후 이용해 주세요."));
		List<FacilityReservation> reservations = facilityReservationRepository.findByUsers(users);
		return reservations.stream().map(this::mapToReservationDetailsDTO).collect(Collectors.toList());
	}

	/**
	 * 시설 예약 내역을 DTO에 담아서 반환하는 메서드
	 * @author Jihwan
	 * @param facilityReservation 시설 예약 내역
	 * @return 예약 내역(시설명, 예약 날짜, 예약 시간, 예약 인원수)
	 */
	public FacilityReservationDetailsDTO mapToReservationDetailsDTO(FacilityReservation facilityReservation) {
		FacilityReservationDetailsDTO dto = new FacilityReservationDetailsDTO();
		dto.setFacilityName(facilityReservation.getFacility().getFName());
		dto.setReservationDate(facilityReservation.getFrDate());
		dto.setReservationTime(facilityReservation.getFrTime());
		dto.setNumberOfPeople(facilityReservation.getFrPeopleNum());
		return dto;
	}

	public void openFacility(Integer facilityId) {
		Optional<Facility> optionalFacility = facilityRepository.findById(facilityId);
		optionalFacility.ifPresent(facility -> facility.setFacilityStatusEnum(FacilityStatusEnum.OPEN));
		facilityRepository.save(optionalFacility.get());
	}

	// 클로우즈 상태값 변경
	public void closeFacility(Integer facilityId) {
		Optional<Facility> optionalFacility = facilityRepository.findById(facilityId);
		optionalFacility.ifPresent(facility -> facility.setFacilityStatusEnum(FacilityStatusEnum.CLOSED));
		facilityRepository.save(optionalFacility.get());
	}

	public List<Facility> getAvailableFacility(Date date, Time time, Integer reservNum) {
		List<Facility> list = facilityRepository.findAll();
		List<FacilityReservation> rsvList = facilityReservationRepository.findByFrDateAndFrTime(date, time);
		List<Facility> returnList = new ArrayList<Facility>();
		for (Facility facility : list) {
			Integer capacity = facility.getFCapacity();
			Integer sum = 0;
			for (FacilityReservation rsv : rsvList) {
				if (rsv.getFacility() == facility) {
					sum += rsv.getFrPeopleNum();
				}
			}
			if (capacity >= sum + reservNum) {
				returnList.add(facility);
			}
		}
		return returnList;
	}

}