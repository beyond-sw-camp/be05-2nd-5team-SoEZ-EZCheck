package com.soez.ezcheck.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soez.ezcheck.entity.Users;

public interface UsersRepository extends JpaRepository<Users, String> {

	/**
	 * 중복 방지를 위해 사용자 ID로 사용자 정보가 존재하는지 확인
	 * @author Jihwan
	 * @param userId 사용자 ID
	 * @return 사용자 정보 존재 여부
	 */
	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Users u WHERE u.uId = :userId")
	boolean existsByUId(@Param("userId") String userId);

	/**
	 * 중복 방지를 위해 사용자 이메일로 사용자 정보가 존재하는지 확인
	 * @author Jihwan
	 * @param email 사용자 이메일
	 * @return 사용자 정보 존재 여부
	 */
	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Users u WHERE u.uEmail = :email")
	boolean existsByUEmail(@Param("email") String email);

	/**
	 * 중복 방지를 위해 사용자 전화번호로 사용자 정보가 존재하는지 확인
	 * @author Jihwan
	 * @param phone 사용자 전화번호
	 * @return 사용자 정보 존재 여부
	 */
	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Users u WHERE u.uPhone = :phone")
	boolean existsByUPhone(@Param("phone") String phone);

	/**
	 * 사용자 ID와 사용자 이메일이 일치하는 사용자 정보가 존재하는지 확인
	 * @author Jihwan
	 * @param userId 사용자 ID
	 * @param email 사용자 이메일
	 * @return 사용자 정보 존재 여부
	 */
	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Users u WHERE u.uId = :userId AND u.uEmail = :email")
	boolean existsByUIdAndUEmail(@Param("userId") String userId, @Param("email") String email);

	/**
	 * 사용자 email로 사용자 정보 조회
	 * @param email 사용자 이메일
	 * @return 사용자 정보
	 */
	@Query("SELECT u FROM Users u WHERE u.uEmail = :email")
	Optional<Users> findByUEmail(@Param("email") String email);

}
