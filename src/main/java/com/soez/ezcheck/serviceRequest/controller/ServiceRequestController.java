package com.soez.ezcheck.serviceRequest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soez.ezcheck.serviceRequest.dto.ServiceRequestDTO;
import com.soez.ezcheck.serviceRequest.service.ServiceRequestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/serviceRequest")
public class ServiceRequestController {
    
    private final   ServiceRequestService serviceRequestService;
    
    @GetMapping("/myServiceRequests")
    public void myRequests(String id){
        serviceRequestService.findMyRequests(id);
    }

    @PostMapping("/make")
    public void makeServiceRequest(@RequestBody ServiceRequestDTO requestDTO){
        serviceRequestService.addServiceRequest(requestDTO);
    }

}