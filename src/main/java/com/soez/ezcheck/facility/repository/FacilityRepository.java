package com.soez.ezcheck.facility.repository;

import com.soez.ezcheck.entity.Facility;
import com.soez.ezcheck.entity.FacilityStatusEnum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Integer> {

	@Query("SELECT f FROM Facility f WHERE f.fCapacity >= :peopleToReserve AND f.facilityStatusEnum = :statusOpen")
	List<Facility> findAvailableFacilities(Integer peopleToReserve, FacilityStatusEnum statusOpen);

}
