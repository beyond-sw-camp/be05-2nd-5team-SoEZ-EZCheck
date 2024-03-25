package com.soez.ezcheck.user.repository;

import com.soez.ezcheck.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String> {

}
