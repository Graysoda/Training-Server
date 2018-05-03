package soap.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap.generated.*;
import soap.service.RentalServiceImpl;

@Endpoint
public class RentalEndpoint {
	private static final String NAMESPACE_URI = Constants.NAMESPACE_URI;
	private RentalServiceImpl rentalService;

	@Autowired
	public void setRentalService(RentalServiceImpl rentalService) {
		this.rentalService = rentalService;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRentalByCustomerIdRequest")
	@ResponsePayload
	public GetRentalByCustomerIdResponse getRentalByCustomerId(@RequestPayload GetRentalByCustomerIdRequest request){
		GetRentalByCustomerIdResponse response = new GetRentalByCustomerIdResponse();
		response.setRental(rentalService.getRentalsByCustomerId(request.getCustomerId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRentalByIdRequest")
	@ResponsePayload
	public GetRentalByIdResponse getRentalById(@RequestPayload GetRentalByIdRequest request){
		GetRentalByIdResponse response = new GetRentalByIdResponse();
		response.setRental(rentalService.getRentalById(request.getRentalId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRentalByReturnDateRequest")
	@ResponsePayload
	public GetRentalByReturnDateResponse getRentalByReturnDate(@RequestPayload GetRentalByReturnDateRequest request){
		GetRentalByReturnDateResponse response = new GetRentalByReturnDateResponse();
		response.setRental(rentalService.getRentalsByReturnDate(request.getReturnDate()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRentalByStaffIdRequest")
	@ResponsePayload
	public GetRentalByStaffIdResponse getRentalByStaffId(@RequestPayload GetRentalByStaffIdRequest request){
		GetRentalByStaffIdResponse response = new GetRentalByStaffIdResponse();
		response.setRental(rentalService.getRentalByStaffId(request.getStaffId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRentalByStartDateRequest")
	@ResponsePayload
	public GetRentalByStartDateResponse getRentalByStartDate(@RequestPayload GetRentalByStartDateRequest request){
		GetRentalByStartDateResponse response = new GetRentalByStartDateResponse();
		response.setRental(rentalService.getRentalByStartDate(request.getRentalStartDate()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createRentalRequest")
	public void insertRental(@RequestPayload CreateRentalRequest request){
		rentalService.insertRental(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteRentalRequest")
	public void deleteRental(@RequestPayload DeleteRentalRequest request){
		rentalService.deleteRental(request.getRentalId());
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateRentalRequest")
	public void updateRental(@RequestPayload UpdateRentalRequest request){
		rentalService.updateRental(request);
	}
}
