package com.soez.ezcheck.facility.repository;

import com.soez.ezcheck.facility.domain.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Integer> {

}
