package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import training.generated.Payment;
import training.service.PaymentServiceImpl;

import java.util.List;

@RestController
public class PaymentController {
    @Autowired @Lazy private PaymentServiceImpl paymentService;

    @RequestMapping(value = "/payment/", method = RequestMethod.GET)
    public List<Payment> getAllPayments(){
        return paymentService.getAllPayments();
    }

//    @PostMapping("/payment")
//    public void createPayment()
}
