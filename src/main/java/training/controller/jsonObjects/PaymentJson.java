package training.controller.jsonObjects;

public class PaymentJson {
    private Float amount;
    private Long customerId;
    private String paymentDate;
    private Long rentalId;
    private Long staffId;

    public PaymentJson() {
    }

    public PaymentJson(Float amount, Long customerId, String paymentDate, Long rentalId, Long staffId) {
        this.amount = amount;
        this.customerId = customerId;
        this.paymentDate = paymentDate;
        this.rentalId = rentalId;
        this.staffId = staffId;
    }

    public Float getAmount() {
        return amount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public Long getRentalId() {
        return rentalId;
    }

    public Long getStaffId() {
        return staffId;
    }
}
