package com.soez.ezcheck.serviceRequest.controller;

import java.net.http.HttpResponse;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soez.ezcheck.entity.ServiceRequest;
import com.soez.ezcheck.entity.Users;
import com.soez.ezcheck.serviceRequest.domain.ServiceMakeRequestDTO;
import com.soez.ezcheck.serviceRequest.domain.ServiceRequestDTO;
import com.soez.ezcheck.serviceRequest.service.ServiceRequestService;

import lombok.RequiredArgsConstructor;

@PreAuthorize("hasAuthority('User')")
@RequiredArgsConstructor
@RestController
@RequestMapping("/serviceRequest")
public class ServiceRequestController {

    private final   ServiceRequestService serviceRequestService;

    @GetMapping("/myServiceRequests")
    public ResponseEntity<List<ServiceRequest>> myRequests(@RequestBody ServiceRequestDTO requestDTO){
        List<ServiceRequest> list = serviceRequestService.findMyRequests(requestDTO);
        if (list.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @PostMapping("/make")
    public ResponseEntity<Boolean> makeServiceRequest(@RequestBody ServiceRequestDTO requestDTO){
        Boolean response = serviceRequestService.addServiceRequest(requestDTO);
        return ResponseEntity.ok(response);
    }

}
