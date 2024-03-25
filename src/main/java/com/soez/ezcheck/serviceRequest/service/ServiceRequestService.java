package com.soez.ezcheck.serviceRequest.service;

import org.springframework.stereotype.Service;

import com.soez.ezcheck.entity.ServiceRequest;
import com.soez.ezcheck.entity.ServiceRequestStatusEnum;
import com.soez.ezcheck.entity.Users;
import com.soez.ezcheck.serviceRequest.domain.ServiceRequestDTO;
import com.soez.ezcheck.serviceRequest.repository.ServiceRequestRepository;
import com.soez.ezcheck.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceRequestService {
    
    private final ServiceRequestRepository serviceRequestRepository;
    private final UserRepository userRepository;
    
    public List<ServiceRequest> findMyRequests(ServiceRequestDTO requestDTO){ /////// 유저 아이디로 서비스 요청 조회
        Optional<Users> OptionalUser = userRepository.findById(requestDTO.getuId());
        if(OptionalUser.isPresent()){
            return serviceRequestRepository.findByUsers(OptionalUser.get());
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public Boolean addServiceRequest(ServiceRequestDTO requestDTO){ /////// 서비스 요청 추가
        ServiceRequest request = new ServiceRequest();
        request.setServiceRequestStatusEnum(ServiceRequestStatusEnum.PENDING);
        request.setServiceRequestTypeEnum(requestDTO.getServiceRequestTypeEnum());
        Optional<Users> OptionalUser = userRepository.findById(requestDTO.getuId());
        if(OptionalUser.isPresent()){
            try{
                request.setUsers(OptionalUser.get());
                serviceRequestRepository.save(request);
                return true;
            } catch (Exception e){
                return false;
            }
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
}
