package com.soez.ezcheck.serviceRequest.controller;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@PreAuthorize("hasAuthority('User')")
@RequiredArgsConstructor
@RestController
@RequestMapping("/serviceRequest")
public class ServiceRequestController {

    private final   ServiceRequestService serviceRequestService;

    @Tag(name = "ServiceRequest-User")
    @GetMapping("/myServiceRequests")
    public ResponseEntity<List<ServiceRequest>> myRequests(@RequestBody ServiceRequestDTO requestDTO){
        List<ServiceRequest> list = serviceRequestService.findMyRequests(requestDTO);
        if (list.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Tag(name = "ServiceRequest-User")
    @PostMapping("/make")
    public ResponseEntity<Boolean> makeServiceRequest(@RequestBody ServiceRequestDTO requestDTO){
        Boolean response = serviceRequestService.addServiceRequest(requestDTO);
        return ResponseEntity.ok(response);
    }


    // 처리 결과 상관 없이 전체 결과 불러오기
    @GetMapping(value = "/findAllServiceRequest")
    public ResponseEntity<List<ServiceRequest>> findAllServiceRequest() {
        List<ServiceRequest> list = serviceRequestService.findAllServiceRequest();
        System.out.println(list);
        return new ResponseEntity<List<ServiceRequest>>(list, HttpStatus.OK);
    }

    //특정 고객이 요청한 서비스 LIST 확인
    @GetMapping(value = "/UidServiceRequest/{uId}" , produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ServiceRequest>> UidServiceRequest(@PathVariable("uId") String uId) {
        List<ServiceRequest> list = serviceRequestService.findServiceRequestByUId(uId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 고객 서비스 요청 처리
    @GetMapping(value = "/updateServiceRequestStatus/{rqId}" , produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> updateServiceRequestStatus(@PathVariable("rqId") Integer rqId) {
        Optional<ServiceRequest> serviceRequest = serviceRequestService.find(rqId);
        String msg;
        if (serviceRequest.isPresent()) {
            msg = serviceRequestService.updateServiceRequestStatus(serviceRequest.get().getRqId());
        } else {
            msg = "서비스 요청 상태 변경에 실패했습니다. 요청번호를 다시 확인해 주세요.";
        }
        serviceRequest.ifPresent(value -> System.out.println("debug >>>>>>> " + value));

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

}
