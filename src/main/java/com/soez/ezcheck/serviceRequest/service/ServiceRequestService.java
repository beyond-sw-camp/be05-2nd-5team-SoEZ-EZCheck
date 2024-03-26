package com.soez.ezcheck.serviceRequest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.soez.ezcheck.entity.ServiceRequest;
import com.soez.ezcheck.entity.ServiceRequestStatusEnum;
import com.soez.ezcheck.entity.Users;
import com.soez.ezcheck.serviceRequest.domain.ServiceRequestDTO;
import com.soez.ezcheck.serviceRequest.repository.ServiceRequestRepository;
import com.soez.ezcheck.user.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceRequestService {

	private final ServiceRequestRepository serviceRequestRepository;
	private final UsersRepository userRepository;

	public List<ServiceRequest> findMyRequests(ServiceRequestDTO requestDTO) { /////// 유저 아이디로 서비스 요청 조회
		Optional<Users> OptionalUser = userRepository.findById(requestDTO.getuId());
		if (OptionalUser.isPresent()) {
			return serviceRequestRepository.findByUsers(OptionalUser.get());
		} else {
			throw new IllegalArgumentException("User not found");
		}
	}

	public Boolean addServiceRequest(ServiceRequestDTO requestDTO) { /////// 서비스 요청 추가
		ServiceRequest request = new ServiceRequest();
		request.setServiceRequestStatusEnum(ServiceRequestStatusEnum.PENDING);
		request.setServiceRequestTypeEnum(requestDTO.getServiceRequestTypeEnum());
		Optional<Users> OptionalUser = userRepository.findById(requestDTO.getuId());
		if (OptionalUser.isPresent()) {
			try {
				request.setUsers(OptionalUser.get());
				serviceRequestRepository.save(request);
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			throw new IllegalArgumentException("User not found");
		}

	}


	public List<ServiceRequest> findAllServiceRequest() {
		List<ServiceRequest> list = serviceRequestRepository.findAll();
		return list;
	}

	public List<ServiceRequest> findServiceRequestByUId(String uId) {
//        List<ServiceRequest> list = repository.UidServiceRequest(uId);
		return serviceRequestRepository.UidServiceRequest(uId);
	}


	public Optional<ServiceRequest> find(Integer rqId) {
		return serviceRequestRepository.findById(rqId);
	}

	public String updateServiceRequestStatus(Integer rqId) {
		Optional<ServiceRequest> data = serviceRequestRepository.findById(rqId);
		if ( data.isPresent() ) {
			data.get().setServiceRequestStatusEnum(ServiceRequestStatusEnum.COMPLETED);
			ServiceRequest updateRequestStatus = serviceRequestRepository.save(data.get());
			return "객실 상태를 변경했습니다^^";
		}
		return "객실 상태 변경에 실패했습니다. 요청자를 확인해 주세요";
	}



}
