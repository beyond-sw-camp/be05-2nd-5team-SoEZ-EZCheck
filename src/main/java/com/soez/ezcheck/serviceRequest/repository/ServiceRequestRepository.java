package com.soez.ezcheck.serviceRequest.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.soez.ezcheck.entity.ServiceRequest;
import com.soez.ezcheck.entity.Users;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Integer>{
    
    public List<ServiceRequest> findByUsers(Users user);
}
