package training.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.api.Common;
import training.api.rest.jsonObjects.PaymentJson;
import training.generated.CreatePaymentRequest;
import training.generated.Payment;
import training.generated.UpdatePaymentRequest;
import training.service.impl.PaymentServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = RestConstants.REST_SERVICES_LOCATION, produces = RestConstants.JSON)
public class PaymentRestController {
    @Autowired @Lazy private PaymentServiceImpl paymentService;

    @RequestMapping(value = "/payments", method = RequestMethod.GET)
    public ResponseEntity<List<Payment>> getAllPayments(){
        return new ResponseEntity<>(paymentService.getAllPayments(), HttpStatus.OK);
    }

    @RequestMapping(value = "/payments", method = RequestMethod.POST)
    public ResponseEntity<?> createPayment(@RequestBody PaymentJson paymentJson){
        CreatePaymentRequest request = new CreatePaymentRequest();

        if (paymentJson.getAmount() != null)
            request.setAmount(paymentJson.getAmount());
        else
            return ResponseEntity.badRequest().body("Payment amount cannot be null.");

        if (paymentJson.getCustomerId() != null && paymentJson.getCustomerId() > 0)
            request.setCustomerId(paymentJson.getCustomerId());
        else
            return ResponseEntity.badRequest().body(Common.idFailureMessage("Payment customerId"));

        if (paymentJson.getPaymentDate() != null)
            request.setPaymentDate(paymentJson.getPaymentDate());
        else
            return ResponseEntity.badRequest().body("Payment paymentDate cannot be null.");

        if (paymentJson.getRentalId() != null && paymentJson.getRentalId() > 0)
            request.setRentalId(paymentJson.getRentalId());
        else
            return ResponseEntity.badRequest().body(Common.idFailureMessage("Payment rentalId"));

        if (paymentJson.getStaffId() != null && paymentJson.getStaffId() > 0)
            request.setStaffId(paymentJson.getStaffId());
        else
            return ResponseEntity.badRequest().body(Common.idFailureMessage("Payment staffId"));

        return paymentService.insertPayment(request);
    }

    @RequestMapping(value = "/payments/{paymentId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePayment(@PathVariable long paymentId, @RequestBody PaymentJson paymentJson){
        UpdatePaymentRequest request = new UpdatePaymentRequest();

        request.setPaymentId(paymentId);


        if (paymentJson.getAmount() != null)
            request.setAmount(paymentJson.getAmount());
        else
            return ResponseEntity.badRequest().body("Payment amount cannot be null.");

        if (paymentJson.getCustomerId() != null && paymentJson.getCustomerId() > 0)
            request.setCustomerId(paymentJson.getCustomerId());
        else
            return ResponseEntity.badRequest().body(Common.idFailureMessage("Payment customerId"));

        if (paymentJson.getPaymentDate() != null)
            request.setPaymentDate(paymentJson.getPaymentDate());
        else
            return ResponseEntity.badRequest().body("Payment paymentDate cannot be null.");

        if (paymentJson.getRentalId() != null && paymentJson.getRentalId() > 0)
            request.setRentalId(paymentJson.getRentalId());
        else
            return ResponseEntity.badRequest().body(Common.idFailureMessage("Payment rentalId"));

        if (paymentJson.getStaffId() != null && paymentJson.getStaffId() > 0)
            request.setStaffId(paymentJson.getStaffId());
        else
            return ResponseEntity.badRequest().body(Common.idFailureMessage("Payment staffId"));

        return paymentService.updatePayment(request);
    }

    @RequestMapping(value = "/payments/{paymentId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePayment(@PathVariable long paymentId){
        return paymentService.deletePayment(paymentId);
    }

    @RequestMapping(value = "/payments/{paymentId}", method = RequestMethod.GET)
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
