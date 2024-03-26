package com.soez.ezcheck.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soez.ezcheck.entity.Users;

public interface UsersRepository extends JpaRepository<Users, String> {

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Users u WHERE u.uId = :userId")
	boolean existsByUId(@Param("userId") String userId);

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Users u WHERE u.uEmail = :email")
	boolean existsByUEmail(@Param("email") String email);

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Users u WHERE u.uPhone = :phone")
	boolean existsByUPhone(@Param("phone") String phone);

	// @Query("SELECT u FROM Users u WHERE u.uId = :userId AND u.uEmail = :email")
	// Optional<Users> findByIdAndEmail(@Param("userId") String userId, @Param("email") String email);

}
