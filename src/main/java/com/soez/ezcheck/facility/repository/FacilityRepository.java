package com.soez.ezcheck.facility.repository;

import com.soez.ezcheck.facility.domain.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Integer> {

//    @Query("SELECT f FROM Facility f WHERE f.capacity >= :totalPeople " +
//            "AND f.facilityId NOT IN (" +
//            " SELECT fr.frId.facilityId FROM FacilityReservation fr " +
//            " WHERE fr.rsvDate = :reservationDate " +
//            " AND (:startTime BETWEEN fr.timeFrom AND fr.timeTo " +
//            " OR :endTime BETWEEN fr.timeFrom AND fr.timeTo)" +
//            ")")
//    List<Facility> listFacilities(
//            int totalPeople,
//            LocalTime startTime,
//            LocalTime endTime,
//            LocalDate reservationDate);

}
