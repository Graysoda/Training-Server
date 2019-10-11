package training.api.rest;

import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.api.ValidationHelper;
import training.generated.CreatePaymentRequest;
import training.generated.Payment;
import training.generated.UpdatePaymentRequest;
import training.persistence.dto.PaymentDto;
import training.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class PaymentsRestController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ValidationHelper validationHelper;

    @GetMapping("/payments")
    public ResponseEntity<List<Payment>> getAllPayments(){
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/payments/{paymentId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable int paymentId){
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }

    @GetMapping("/rental/{rentalId}/payments")
    public ResponseEntity<List<Payment>> getRentalPayments(@PathVariable int rentalId){
        return ResponseEntity.ok(paymentService.getRentalPayments(rentalId));
    }

    @GetMapping("/customer{customerId}/payments")
    public ResponseEntity<List<Payment>> getCustomerPayments(@PathVariable int customerId){
        return ResponseEntity.ok(paymentService.getCustomerPayments(customerId));
    }

    @PostMapping(value = "/payments", consumes = "application/json")
    public ResponseEntity<?> createPayment(@RequestBody CreatePaymentRequest request){
        PaymentDto payment = new PaymentDto(request);
        String error = validationHelper.validatePaymentCreation(payment);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(paymentService.save(payment)) :
                ResponseEntity.badRequest().body(error);
    }

    @PutMapping(value = "/payments/{paymentId}", consumes = "application/json")
    public ResponseEntity<?> updatePayment(@PathVariable int paymentId, @RequestBody UpdatePaymentRequest request){
        PaymentDto payment = new PaymentDto(request);
        String error = validationHelper.validatePaymentUpdate(paymentId, payment);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(paymentService.save(payment)) :
                ResponseEntity.badRequest().body(error);
    }

    @PutMapping(value = "/payments", consumes = "application/json")
    public ResponseEntity<?> updatePayment(@RequestBody UpdatePaymentRequest request){
        PaymentDto payment = new PaymentDto(request);
        String error = validationHelper.validatePaymentUpdate(payment);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(paymentService.save(payment)) :
                ResponseEntity.badRequest().body(error);
    }

    @DeleteMapping("/payments/{paymentId}")
    public ResponseEntity<?> deletePayment(@PathVariable int paymentId){
        if (paymentId <= 0 || !paymentService.exists(paymentId)){
            return ResponseEntity.badRequest().body("payment id is invalid.");
        }
        paymentService.delete(paymentId);
        return ResponseEntity.ok("payment with id [" + paymentId + "] was deleted.");
    }
}
