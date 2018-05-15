package training.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import training.generated.*;
import training.service.PaymentServiceImpl;

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

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createPaymentRequest")
    @ResponsePayload
	public ResponseEntity<?> insertPayment(@RequestPayload CreatePaymentRequest request){
		return paymentService.insertPayment(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deletePaymentRequest")
    @ResponsePayload
	public ResponseEntity<?> deletePayment(@RequestPayload DeletePaymentRequest request){
		return paymentService.deletePayment(request.getPaymentId());
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updatePaymentRequest")
    @ResponsePayload
	public ResponseEntity<?> updatePayment(@RequestPayload UpdatePaymentRequest request){
		return paymentService.updatePayment(request);
	}
}
