package com.soez.ezcheck.facility.repository;

import com.soez.ezcheck.facility.domain.FacilityReservation;
import com.soez.ezcheck.facility.domain.FacilityReservationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityReservationRepository extends JpaRepository<FacilityReservation, FacilityReservationId> {

}
