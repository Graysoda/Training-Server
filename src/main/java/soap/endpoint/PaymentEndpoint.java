package soap.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap.generated.*;
import soap.service.PaymentServiceImpl;

@Endpoint
public class PaymentEndpoint {
	private static final String NAMESPACE_URI = Constants.NAMESPACE_URI;
	@Autowired @Lazy
	private PaymentServiceImpl paymentService;

//	@Autowired
//	public void setPaymentService(@Lazy PaymentServiceImpl paymentService) {
//		this.paymentService = paymentService;
//	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllPaymentsRequest")
	@ResponsePayload
	public GetAllPaymentsResponse getAllPayments(@RequestPayload GetAllPaymentsRequest request){
		GetAllPaymentsResponse response = new GetAllPaymentsResponse();
		response.setPayment(paymentService.getAllPayments());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createPaymentRequest")
	public void insertPayment(@RequestPayload CreatePaymentRequest request){
		paymentService.insertPayment(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deletePaymentRequest")
	public void deletePayment(@RequestPayload DeletePaymentRequest request){
		paymentService.deletePayment(request.getPaymentId());
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updatePaymentRequest")
	public void updatePayment(@RequestPayload UpdatePaymentRequest request){
		paymentService.updatePayment(request);
	}
}
