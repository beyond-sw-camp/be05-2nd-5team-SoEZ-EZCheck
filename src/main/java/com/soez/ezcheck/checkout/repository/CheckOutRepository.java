package com.soez.ezcheck.checkout.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soez.ezcheck.entity.CheckOut;

@Repository
public interface CheckOutRepository extends JpaRepository<CheckOut, Integer> {

	@Query("SELECT c FROM CheckOut c ORDER BY c.coutDate DESC, c.coutTime DESC")
	List<CheckOut> getAllCheckOutRecords();

	@Query("SELECT c FROM CheckOut c WHERE c.coutDate = :selectedDate ORDER BY c.coutDate DESC, c.coutTime DESC")
	List<CheckOut> getCheckOutRecordsByDate(@Param("selectedDate") Date selectedDate);

}
