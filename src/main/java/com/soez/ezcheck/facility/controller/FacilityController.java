package com.soez.ezcheck.facility.controller;

import com.soez.ezcheck.entity.Facility;
import com.soez.ezcheck.facility.domain.FacilityReservationDetailsDTO;
import com.soez.ezcheck.facility.domain.FacilityReservationRequestDTO;
import com.soez.ezcheck.facility.service.FacilityServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/facility")
@RequiredArgsConstructor
public class FacilityController {

    private final FacilityServiceImpl facilityService;

    @GetMapping("/list")
    public ResponseEntity<List<Facility>> listFacilities(@RequestBody FacilityReservationRequestDTO requestDTO) {
        Integer peopleToReserve = requestDTO.getPeopleToReserve();
        Date date = requestDTO.getDate();
        Time time = requestDTO.getTime();

        List<Facility> availableFacilities = facilityService.findAvailableFacilities(peopleToReserve);

        if (availableFacilities.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }

        // Make reservations for reservable facilities
        List<Facility> reservableFacilities = facilityService.findReservableFacilities(availableFacilities, peopleToReserve, date, time);

        return new ResponseEntity<>(reservableFacilities, HttpStatus.OK);
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> makeReservation(@RequestBody FacilityReservationRequestDTO requestDTO) {
        String msg = facilityService.makeReservation(requestDTO);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping("/user/{uId}")
    public List<FacilityReservationDetailsDTO> getReservationDetails(@PathVariable("uId") String uId) {
        return facilityService.getReservationDetails(uId);
    }

}
