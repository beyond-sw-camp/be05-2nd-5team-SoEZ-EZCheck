package com.soez.ezcheck.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soez.ezcheck.entity.Users;

public interface UserRepository extends JpaRepository<Users, String>{
    
}
