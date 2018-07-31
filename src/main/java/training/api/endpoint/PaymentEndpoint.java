package training.api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import training.api.Common;
import training.generated.*;
import training.service.impl.PaymentServiceImpl;

@Endpoint
public class PaymentEndpoint {
	private static final String NAMESPACE_URI = SoapConstants.NAMESPACE_URI;
	@Autowired @Lazy private PaymentServiceImpl paymentService;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllPaymentsRequest")
	@ResponsePayload
	public GetAllPaymentsResponse getAllPayments(@RequestPayload GetAllPaymentsRequest request){
		GetAllPaymentsResponse response = new GetAllPaymentsResponse();
		response.setPayment(paymentService.getAllPayments());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPaymentByIdRequest")
	@ResponsePayload
	public GetPaymentByIdResponse getPaymentById(@RequestPayload GetPaymentByIdRequest request){
		GetPaymentByIdResponse response = new GetPaymentByIdResponse();
		response.setPayment(paymentService.getPaymentById(request.getPaymentId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPaymentsForRentalRequest")
	@ResponsePayload
	public GetPaymentsForRentalResponse getPaymentsForRental(@RequestPayload GetPaymentsForRentalRequest request){
		GetPaymentsForRentalResponse response = new GetPaymentsForRentalResponse();
		response.setPayment(paymentService.getPaymentsForRental(request.getRentalId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPaymentsFromCustomerRequest")
	@ResponsePayload
	public GetPaymentsFromCustomerResponse getPaymentsFromCustomer(@RequestPayload GetPaymentsFromCustomerRequest request){
		GetPaymentsFromCustomerResponse response = new GetPaymentsFromCustomerResponse();
		response.setPayment(paymentService.getPaymentFromCustomer(request.getCustomerId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createPaymentRequest")
    @ResponsePayload
	public CreatePaymentResponse insertPayment(@RequestPayload CreatePaymentRequest request){
		CreatePaymentResponse response = new CreatePaymentResponse();

		if (request.getStaffId() < 0){
			response.setError("staffId is invalid");
			return response;
		}

		if (request.getRentalId() < 0){
			response.setError("rentalId is invalid");
			return response;
		}

		if (request.getPaymentDate().isEmpty()){
			response.setError("paymentDate cannot be empty");
			return response;
		} else if (!Common.isStringSafe(request.getPaymentDate())){
			response.setError(Common.stringFailureMessage("Payment paymentDate"));
			return response;
		}

		if (request.getCustomerId() < 0){
			response.setError("customerId is invalid");
			return response;
		}

		if (request.getAmount() < 0){
			response.setError("amount is invalid");
			return response;
		}

		ResponseEntity entity = paymentService.insertPayment(request);

		if (entity.getBody() instanceof Payment){
			response.setPayment((Payment) entity.getBody());
			return response;
		} else {
			response.setError(entity.getBody().toString());
			return response;
		}

	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deletePaymentRequest")
    @ResponsePayload
	public DeletePaymentResponse deletePayment(@RequestPayload DeletePaymentRequest request){
		DeletePaymentResponse response = new DeletePaymentResponse();

		if (request.getPaymentId() < 0){
			response.setResponse("paymentId is invalid");
			return response;
		}
		ResponseEntity entity = paymentService.deletePayment(request.getPaymentId());

		response.setResponse(entity.getBody().toString());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updatePaymentRequest")
    @ResponsePayload
	public UpdatePaymentResponse updatePayment(@RequestPayload UpdatePaymentRequest request){
		UpdatePaymentResponse response = new UpdatePaymentResponse();

		if (request.getPaymentId() < 0){
			response.setError("paymentId is invalid");
			return response;
		}

		if (request.getStaffId() < 0){
			response.setError("staffId is invalid");
			return response;
		}

		if (request.getRentalId() < 0){
			response.setError("rentalId is invalid");
			return response;
		}

		if (request.getPaymentDate().isEmpty()){
			response.setError("paymentDate cannot be empty");
			return response;
		} else if (!Common.isStringSafe(request.getPaymentDate())){
			response.setError(Common.stringFailureMessage("Payment paymentDate"));
			return response;
		}

		if (request.getCustomerId() < 0){
			response.setError("customerId is invalid");
			return response;
		}

		if (request.getAmount() < 0){
			response.setError("amount is invalid");
			return response;
		}

		ResponseEntity entity = paymentService.updatePayment(request);

		if (entity.getBody() instanceof Payment){
			response.setPayment((Payment) entity.getBody());
			return response;
		} else {
			response.setError(entity.getBody().toString());
			return response;
		}
	}
}
