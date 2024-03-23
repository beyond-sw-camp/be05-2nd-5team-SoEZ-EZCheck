package com.soez.ezcheck.serviceRequest.controller;

import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soez.ezcheck.entity.ServiceRequest;
import com.soez.ezcheck.serviceRequest.dto.ServiceRequestDTO;
import com.soez.ezcheck.serviceRequest.service.ServiceRequestService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/serviceRequest")
public class ServiceRequestController {
    
    private final   ServiceRequestService serviceRequestService;
    
    @GetMapping("/myServiceRequests")
    public ResponseEntity<List<ServiceRequest>> myRequests(@RequestBody ServiceRequestDTO requestDTO){
        List<ServiceRequest> list = serviceRequestService.findMyRequests(requestDTO);
        if (list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }
    }

    @PostMapping("/make")
    public void makeServiceRequest(@RequestBody ServiceRequestDTO requestDTO){
        serviceRequestService.addServiceRequest(requestDTO);
    }

}
