package com.soez.ezcheck.checkout.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soez.ezcheck.entity.CheckIn;
import com.soez.ezcheck.entity.CheckOut;

@Repository
public interface CheckOutRepository extends JpaRepository<CheckOut, Integer> {

	/**
	 * 모든 체크아웃 기록을 최신순으로 정렬하여 반환
	 * @author Jihwan
	 * @return 모든 체크아웃 기록
	 */
	@Query("SELECT c FROM CheckOut c ORDER BY c.coutDate DESC, c.coutTime DESC")
	List<CheckOut> getAllCheckOutRecords();

	/**
	 * 선택한 날짜의 체크아웃 기록을 최신순으로 정렬하여 반환
	 * @author Jihwan
	 * @param selectedDate 선택한 날짜
	 * @return 선택한 날짜의 체크아웃 기록
	 */
	@Query("SELECT c FROM CheckOut c WHERE c.coutDate = :selectedDate ORDER BY c.coutDate DESC, c.coutTime DESC")
	List<CheckOut> getCheckOutRecordsByDate(@Param("selectedDate") Date selectedDate);

	@Query("SELECT co FROM CheckOut co WHERE co.checkIn = :checkIn")
    Optional<CheckOut> findByCheckIn(@Param("checkIn") CheckIn checkIn);
}
