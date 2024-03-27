package com.soez.ezcheck.facility.repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soez.ezcheck.entity.FacilityReservation;
import com.soez.ezcheck.entity.Users;

@Repository
public interface FacilityReservationRepository extends JpaRepository<FacilityReservation, Integer> {

	List<FacilityReservation> findByFrDateAndFrTime(Date frDate, Time frTime);

	List<FacilityReservation> findByUsers(Users users);

}
