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
	public CreateRentalResponse insertRental(@RequestPayload CreateRentalRequest request){
		CreateRentalResponse response = new CreateRentalResponse();

		if (request.getStaffId() < 0){
			response.setError("staffId is invalid");
			return response;
		}
		if (request.getReturnDate().equals("") || request.getReturnDate().isEmpty()){
			response.setError("returnDate is invalid");
			return response;
		}
		if (request.getRentalDate().equals("") || request.getRentalDate().isEmpty()){
			response.setError("rentalDate is invalid");
			return response;
		}
		if (request.getInventoryId() < 0){
			response.setError("inventoryId is invalid");
			return response;
		}
		if (request.getCustomerId() < 0){
			response.setError("customerId is invalid");
			return response;
		}

		ResponseEntity entity = rentalService.insertRental(request);

		if (entity.getBody() instanceof Rental){
			response.setRental((Rental) entity.getBody());
			return response;
		} else {
			response.setError(entity.getBody().toString());
			return response;
		}
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteRentalRequest")
	@ResponsePayload
	public DeleteRentalResponse deleteRental(@RequestPayload DeleteRentalRequest request){
		DeleteRentalResponse response = new DeleteRentalResponse();

		if (request.getRentalId() < 0){
			response.setResponse("rentalId is invalid");
			return response;
		}

		ResponseEntity entity = rentalService.deleteRental(request.getRentalId());

		response.setResponse(entity.getBody().toString());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateRentalRequest")
	@ResponsePayload
	public UpdateRentalResponse updateRental(@RequestPayload UpdateRentalRequest request){
		UpdateRentalResponse response = new UpdateRentalResponse();

		if (request.getRentalId() < 0){
			response.setError("rentalId is invalid");
			return response;
		}

		ResponseEntity entity = rentalService.updateRental(request);

		if (entity.getBody() instanceof Rental){
			response.setRental((Rental) entity.getBody());
			return response;
		} else {
			response.setError(entity.getBody().toString());
			return response;
		}
	}
}
