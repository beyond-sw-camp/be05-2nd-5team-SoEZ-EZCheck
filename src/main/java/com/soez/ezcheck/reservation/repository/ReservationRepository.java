package com.soez.ezcheck.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soez.ezcheck.entity.Reservation;
import com.soez.ezcheck.entity.Users;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	List<Reservation> findByUsers(Users users);

	@Modifying
	@Query("DELETE FROM Reservation r WHERE r.users.uId = :userId")
	void deleteByUserId(@Param("userId") String userId);

	// @Modifying
	// @Query("DELETE FROM Reservation r WHERE r.users.uId = :userId")
	// List<Reservation> findByUserId(String uId);

}
