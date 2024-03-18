package com.soez.ezcheck.facility.service;

import com.soez.ezcheck.facility.domain.Facility;
import com.soez.ezcheck.facility.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service("facilities")
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;

//    public List<Facility> listFacilities(
//            int totalPeople,
//            LocalTime startTime,
//            LocalTime endTime,
//            LocalDate reservationDate) {
//        return facilityRepository.listFacilities(totalPeople, startTime, endTime, reservationDate);
//    }

}
