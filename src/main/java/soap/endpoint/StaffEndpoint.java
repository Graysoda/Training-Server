package soap.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap.generated.*;
import soap.service.StaffServiceImpl;

@Endpoint
public class StaffEndpoint {
	private static final String NAMESPACE_URI = Constants.NAMESPACE_URI;
	@Autowired private StaffServiceImpl staffService;

//	@Autowired
//	public void setStaffService(@Lazy StaffServiceImpl staffService) {
//		this.staffService = staffService;
//	}

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
	public void insertStaff(@RequestPayload CreateStaffRequest request){
		staffService.insertStaff(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteStaffRequest")
	public void deleteStaff(@RequestPayload DeleteStaffRequest request){
		staffService.deleteStaff(request.getStaffId());
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateStaffRequest")
	public void updateStaff(@RequestPayload UpdateStaffRequest request){
		staffService.updateStaff(request);
	}
}
