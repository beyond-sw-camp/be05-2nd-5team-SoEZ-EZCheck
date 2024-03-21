package com.soez.ezcheck.facility.service;

import com.soez.ezcheck.entity.Facility;
import com.soez.ezcheck.entity.FacilityReservation;
import com.soez.ezcheck.entity.FacilityStatusEnum;
import com.soez.ezcheck.facility.domain.FacilityReservationRequestDTO;
import com.soez.ezcheck.facility.repository.FacilityRepository;
import com.soez.ezcheck.facility.repository.FacilityReservationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityServiceImpl {

    private final FacilityRepository facilityRepository;

    private final FacilityReservationRepository facilityReservationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Facility> findAvailableFacilities(Integer peopleToReserve) {
        return facilityRepository.findAvailableFacilities(peopleToReserve, FacilityStatusEnum.OPEN);
    }

    @Transactional
    public List<Facility> findReservableFacilities(List<Facility> availableFacilities, Integer peopleToReserve, Date date, Time time) {
        List<Facility> reservableFacilities = new ArrayList<>();
        for (Facility facility : availableFacilities) {
            // Check if the facility can accommodate the total number of people
            int currentReservationCount = getCurrentReservationCount(facility, date, time);
            if (currentReservationCount + peopleToReserve <= facility.getFCapacity()) {
                // add to reservable facility list
                reservableFacilities.add(facility);
            }
        }
        return reservableFacilities;
    }

    private int getCurrentReservationCount(Facility facility, Date date, Time time) {
        List<FacilityReservation> reservations = facilityReservationRepository.findByFrDateAndFrTime(date, time);
        int count = 0;
        for (FacilityReservation reservation : reservations) {
            if (reservation.getFacility().getFId().equals(facility.getFId())) {
                count += reservation.getFrPeopleNum();
            }
        }
        return count;
    }

    @Transactional
    public String makeReservation(FacilityReservationRequestDTO requestDTO) {
        // storing reservation record to the database

        // Prepare the SQL query string
        String sql = "INSERT INTO facility_reservation (fr_date, fr_time, fr_people_num, f_id, u_id) " +
                "VALUES (:frDate, :frTime, :frPeopleNum, :fId, :uId)";

        // Create the query
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("frDate", requestDTO.getDate());
        query.setParameter("frTime", requestDTO.getTime());
        query.setParameter("frPeopleNum", requestDTO.getPeopleToReserve());
        query.setParameter("fId", requestDTO.getFacilityId());
        query.setParameter("uId", requestDTO.getUserId());

        // Execute the query
        query.executeUpdate();

        return requestDTO.getDate().toString() + " " + requestDTO.getTime().toString() + " 에 예약이 완료되었습니다";
    }

}
