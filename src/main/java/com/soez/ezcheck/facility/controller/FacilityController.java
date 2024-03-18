package com.soez.ezcheck.facility.controller;

import com.soez.ezcheck.facility.domain.Facility;
import com.soez.ezcheck.facility.service.FacilityService;
import jakarta.annotation.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/facilities")
public class FacilityController {

    @Resource(name = "facilities")
    private FacilityService facilityService;

//    @GetMapping("/facilities/available")
//    public List<Facility> listFacilities(
//            @RequestParam int totalPeople,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reservationDate) {
//        return facilityService.listFacilities(totalPeople, startTime, endTime, reservationDate);
//    }

}