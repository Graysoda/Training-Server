package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.generated.CreatePaymentRequest;
import training.generated.Payment;
import training.generated.UpdatePaymentRequest;
import training.service.PaymentServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = RestConstants.REST_SERVICES_LOCATION, produces = RestConstants.JSON)
public class PaymentController {
    @Autowired @Lazy private PaymentServiceImpl paymentService;

    @RequestMapping(value = "/payments/", method = RequestMethod.GET)
    public ResponseEntity<List<Payment>> getAllPayments(){
        return new ResponseEntity<>(paymentService.getAllPayments(), HttpStatus.OK);
    }

    @RequestMapping(value = "/payments/create", method = RequestMethod.PUT)
    public ResponseEntity<?> createPayment(@RequestBody CreatePaymentRequest request){
        return paymentService.insertPayment(request);
    }

    @RequestMapping(value = "/payments/update", method = RequestMethod.POST)
    public ResponseEntity<?> updatePayment(@RequestBody UpdatePaymentRequest request){
        return paymentService.updatePayment(request);
    }

    @RequestMapping(value = "/payments/{paymentId}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePayment(@PathVariable long paymentId){
        return paymentService.deletePayment(paymentId);
    }

    @RequestMapping(value = "/payments/{paymentId}/", method = RequestMethod.GET)
    public ResponseEntity<?> getPaymentById(@PathVariable long paymentId){
        return new ResponseEntity<>(paymentService.getPaymentById(paymentId), HttpStatus.OK);
    }

    @RequestMapping(value = "/rental/{rentalId}/payments", method = RequestMethod.GET)
    public ResponseEntity<?> getPaymentsForRental(@PathVariable long rentalId){
        return new ResponseEntity<>(paymentService.getPaymentsForRental(rentalId), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/{customerId}/payments", method = RequestMethod.GET)
    public ResponseEntity<?> getPaymentsFromCustomer(@PathVariable long customerId){
        return new ResponseEntity<>(paymentService.getPaymentFromCustomer(customerId), HttpStatus.OK);
    }
}
