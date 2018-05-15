package training.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import training.generated.*;
import training.service.StaffServiceImpl;

@Endpoint
public class StaffEndpoint {
	private static final String NAMESPACE_URI = SoapConstants.NAMESPACE_URI;
	@Autowired @Lazy private StaffServiceImpl staffService;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllStaffRequest")
	@ResponsePayload
	public GetAllStaffResponse getAllStaff(@RequestPayload GetAllStaffRequest request){
		GetAllStaffResponse response = new GetAllStaffResponse();
		response.setStaff(staffService.getAllStaff());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getStaffByIdRequest")
	@ResponsePayload
	public GetStaffByIdResponse getStaffById(@RequestPayload GetStaffByIdRequest request){
		GetStaffByIdResponse response = new GetStaffByIdResponse();
		response.setStaff(staffService.getStaffById(request.getStaffId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createStaffRequest")
	public ResponseEntity<?> insertStaff(@RequestPayload CreateStaffRequest request){
		return staffService.insertStaff(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteStaffRequest")
	public ResponseEntity<?> deleteStaff(@RequestPayload DeleteStaffRequest request){
		return staffService.deleteStaff(request.getStaffId());
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateStaffRequest")
	public ResponseEntity<?> updateStaff(@RequestPayload UpdateStaffRequest request){
		return staffService.updateStaff(request);
	}
}
