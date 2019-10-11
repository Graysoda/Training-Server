package training.persistence.dto;

import lombok.Data;
import training.Constants;
import training.generated.CreatePaymentRequest;
import training.generated.UpdatePaymentRequest;
import training.persistence.entity.Customer;
import training.persistence.entity.Payment;
import training.persistence.entity.Rental;
import training.persistence.entity.Staff;

import java.util.Objects;

@Data
public class PaymentDto {
    private Integer id;
    private Float amount;
    private Integer customer;
    private String paymentDate;
    private Integer rental;
    private Integer staff;

    public PaymentDto(){}

    public PaymentDto(CreatePaymentRequest request) {
        amount = request.getAmount();
        customer = request.getCustomerId();
        paymentDate = request.getPaymentDate();
        rental = request.getRentalId();
        staff = request.getRentalId();
    }

    public PaymentDto(UpdatePaymentRequest request) {
        id = request.getPaymentId();
        amount = request.getAmount();
        customer = request.getCustomerId();
        rental = request.getRentalId();
        staff = request.getStaffId();
        paymentDate = request.getPaymentDate();
    }

    public Payment makeEntity(Customer customer, Rental rental, Staff staff){
        Payment payment = new Payment();
        payment.setId(id);
        payment.setAmount(amount);
        payment.setCustomer(customer);
        payment.setPaymentDate(Constants.formatString(paymentDate));
        payment.setRental(rental);
        payment.setStaff(staff);
        return payment;
    }

    public Payment makeEntity(Payment p) {
        if (Objects.isNull(amount)){
            amount = p.getAmount();
        }
        if (Objects.isNull(paymentDate)){
            paymentDate = Constants.formatDate(p.getPaymentDate());
        }
        return makeEntity(p.getCustomer(), p.getRental(), p.getStaff());
    }
}
