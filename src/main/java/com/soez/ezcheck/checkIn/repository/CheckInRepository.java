package com.soez.ezcheck.checkIn.repository;

import com.soez.ezcheck.entity.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
}
