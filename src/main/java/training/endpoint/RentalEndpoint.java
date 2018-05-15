package training.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import training.generated.*;
import training.service.RentalServiceImpl;

@Endpoint
public class RentalEndpoint {
	private static final String NAMESPACE_URI = SoapConstants.NAMESPACE_URI;
	@Autowired @Lazy private RentalServiceImpl rentalService;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllRentalsRequest")
	@ResponsePayload
	public GetAllRentalsResponse getAllRentals(@RequestPayload GetAllRentalsRequest request){
		GetAllRentalsResponse response = new GetAllRentalsResponse();
		response.setRental(rentalService.getAllRentals());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRentalByCustomerIdRequest")
	@ResponsePayload
	public GetRentalByCustomerIdResponse getRentalByCustomerId(@RequestPayload GetRentalByCustomerIdRequest request) {
		GetRentalByCustomerIdResponse response = new GetRentalByCustomerIdResponse();
		response.setRental(rentalService.getRentalsByCustomerId(request.getCustomerId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRentalByIdRequest")
	@ResponsePayload
	public GetRentalByIdResponse getRentalById(@RequestPayload GetRentalByIdRequest request) {
		GetRentalByIdResponse response = new GetRentalByIdResponse();
		response.setRental(rentalService.getRentalById(request.getRentalId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRentalByReturnDateRequest")
	@ResponsePayload
	public GetRentalByReturnDateResponse getRentalByReturnDate(@RequestPayload GetRentalByReturnDateRequest request) {
		GetRentalByReturnDateResponse response = new GetRentalByReturnDateResponse();
		response.setRental(rentalService.getRentalsByReturnDate(request.getReturnDate()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRentalByStaffIdRequest")
	@ResponsePayload
	public GetRentalByStaffIdResponse getRentalByStaffId(@RequestPayload GetRentalByStaffIdRequest request) {
		GetRentalByStaffIdResponse response = new GetRentalByStaffIdResponse();
		response.setRental(rentalService.getRentalByStaffId(request.getStaffId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRentalByStartDateRequest")
	@ResponsePayload
	public GetRentalByStartDateResponse getRentalByStartDate(@RequestPayload GetRentalByStartDateRequest request) {
		GetRentalByStartDateResponse response = new GetRentalByStartDateResponse();
		response.setRental(rentalService.getRentalByStartDate(request.getRentalStartDate()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createRentalRequest")
	@ResponsePayload
	public ResponseEntity<?> insertRental(@RequestPayload CreateRentalRequest request){
		return rentalService.insertRental(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteRentalRequest")
	@ResponsePayload
	public ResponseEntity<?> deleteRental(@RequestPayload DeleteRentalRequest request){
		return rentalService.deleteRental(request.getRentalId());
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateRentalRequest")
	@ResponsePayload
	public ResponseEntity<?> updateRental(@RequestPayload UpdateRentalRequest request){
		return rentalService.updateRental(request);
	}
}
