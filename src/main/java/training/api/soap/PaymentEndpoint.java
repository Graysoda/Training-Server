package training.api.soap;

import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import training.Constants;
import training.api.ValidationHelper;
import training.generated.*;
import training.persistence.dto.PaymentDto;
import training.service.CustomerService;
import training.service.PaymentService;
import training.service.RentalService;

@Endpoint
public class PaymentEndpoint {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ValidationHelper validationHelper;
    @Autowired
    private RentalService rentalService;
    @Autowired
    private CustomerService customerService;

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getAllPaymentsRequest")
    @ResponsePayload
    public GetAllPaymentsResponse getAllPayments(@RequestPayload GetAllPaymentsRequest request){
        GetAllPaymentsResponse response = new GetAllPaymentsResponse();
        response.setPayment(paymentService.getAllPayments());
        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getPaymentByIdRequest")
    @ResponsePayload
    public GetPaymentByIdResponse getPaymentById(@RequestPayload GetPaymentByIdRequest request){
        GetPaymentByIdResponse response = new GetPaymentByIdResponse();

        if (paymentService.exists(request.getPaymentId())){
            response.setPayment(paymentService.getPaymentById(request.getPaymentId()));
        } else {
            response.setError("payment " + Constants.DNE);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getPaymentsForRentalRequest")
    @ResponsePayload
    public GetPaymentsForRentalResponse getPaymentsForRental(@RequestPayload GetPaymentsForRentalRequest request){
        GetPaymentsForRentalResponse response = new GetPaymentsForRentalResponse();

        if (rentalService.exists(request.getRentalId())){
            response.setPayment(paymentService.getRentalPayments(request.getRentalId()));
        } else {
            response.setError("rental " + Constants.DNE);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getPaymentsFromCustomerRequest")
    @ResponsePayload
    public GetPaymentsFromCustomerResponse getPaymentsFromCustomer(@RequestPayload GetPaymentsFromCustomerRequest request){
        GetPaymentsFromCustomerResponse response = new GetPaymentsFromCustomerResponse();

        if (customerService.exists(request.getCustomerId())){
            response.setPayment(paymentService.getCustomerPayments(request.getCustomerId()));
        } else {
            response.setError("customer " + Constants.DNE);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "createPaymentRequest")
    @ResponsePayload
    public CreatePaymentResponse createPayment(@RequestPayload CreatePaymentRequest request){
        CreatePaymentResponse response = new CreatePaymentResponse();
        PaymentDto payment = new PaymentDto(request);
        String error = validationHelper.validatePaymentCreation(payment);

        if (StringHelper.isNullOrEmptyString(error)){
            response.setPayment(paymentService.save(payment));
        } else {
            response.setError(error);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "updatePaymentRequest")
    @ResponsePayload
    public UpdatePaymentResponse updatePayment(@RequestPayload UpdatePaymentRequest request){
        UpdatePaymentResponse response = new UpdatePaymentResponse();
        PaymentDto payment = new PaymentDto(request);
        String error = validationHelper.validatePaymentUpdate(payment);

        if (StringHelper.isNullOrEmptyString(error)){
            response.setPayment(paymentService.save(payment));
        } else {
            response.setError(error);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "deletePaymentRequest")
    @ResponsePayload
    public DeletePaymentResponse deletePayment(@RequestPayload DeletePaymentRequest request){
        DeletePaymentResponse response = new DeletePaymentResponse();

        if (request.getPaymentId() <= 0 || !paymentService.exists(request.getPaymentId())){
            response.setResponse("payment id is invalid.");
        } else {
            paymentService.delete(request.getPaymentId());
            response.setResponse("payment with id [" + request.getPaymentId() + "] was deleted.");
        }

        return response;
    }
}
