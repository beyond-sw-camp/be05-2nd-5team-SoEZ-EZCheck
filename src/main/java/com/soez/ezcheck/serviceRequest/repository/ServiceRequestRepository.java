package com.soez.ezcheck.serviceRequest.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.soez.ezcheck.entity.ServiceRequest;
import com.soez.ezcheck.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Integer>{
    
    public List<ServiceRequest> findByUsers(Users user);


    @Query("SELECT SR FROM ServiceRequest SR WHERE SR.users.uId = :uId")
    List<ServiceRequest> UidServiceRequest(@Param("uId") String uId);


}
