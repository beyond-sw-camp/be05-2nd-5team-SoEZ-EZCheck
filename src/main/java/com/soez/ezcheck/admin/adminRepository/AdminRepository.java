package com.soez.ezcheck.admin.adminRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soez.ezcheck.entity.Admin;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String>{
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Admin u WHERE u.uId = :userId")
	boolean existsByUId(@Param("userId") String userId);

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Admin u WHERE u.uEmail = :email")
	boolean existsByUEmail(@Param("email") String email);

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Admin u WHERE u.uPhone = :phone")
	boolean existsByUPhone(@Param("phone") String phone);


    @Query("SELECT u FROM Admin u WHERE u.uEmail = :email")
    Optional<Admin> findByUEmail(@Param("email") String email);
}
