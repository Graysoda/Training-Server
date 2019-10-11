package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.generated.Payment;
import training.persistence.dao.CustomerDao;
import training.persistence.dao.PaymentDao;
import training.persistence.dao.RentalDao;
import training.persistence.dao.StaffDao;
import training.persistence.dto.PaymentDto;
import training.persistence.entity.Customer;
import training.persistence.entity.Rental;
import training.persistence.entity.Staff;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PaymentService {
    @Autowired
    private PaymentDao paymentDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private RentalDao rentalDao;
    @Autowired
    private StaffDao staffDao;

    public List<Payment> getAllPayments() {
        return convert(paymentDao.findAll());
    }

    private List<Payment> convert(Iterable<training.persistence.entity.Payment> all) {
        List<Payment> payments = new ArrayList<>();
        all.forEach(p -> payments.add(p.makeGenerated()));
        return payments;
    }

    public Payment getPaymentById(int paymentId) {
        return paymentDao.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(paymentId))).makeGenerated();
    }

    public List<Payment> getRentalPayments(int rentalId) {
        return convert(paymentDao.findByRentalId(rentalId));
    }

    public List<Payment> getCustomerPayments(int customerId) {
        return convert(paymentDao.findByCustomerId(customerId));
    }

    public Payment save(PaymentDto payment) {
        training.persistence.entity.Payment entity;

        if (Objects.nonNull(payment.getId())){
            training.persistence.entity.Payment p = paymentDao.findById(payment.getId())
                    .orElseThrow(() -> new EntityNotFoundException(payment.getId().toString()));

            if (Objects.nonNull(payment.getCustomer())){
                p.setCustomer(customerDao.findById(payment.getCustomer())
                        .orElseThrow(() -> new EntityNotFoundException(payment.getCustomer().toString())));
            }
            if (Objects.nonNull(payment.getRental())){
                p.setRental(rentalDao.findById(payment.getRental())
                        .orElseThrow(() -> new EntityNotFoundException(payment.getRental().toString())));
            }
            if (Objects.nonNull(payment.getStaff())){
                p.setStaff(staffDao.findById(payment.getStaff())
                        .orElseThrow(() -> new EntityNotFoundException(payment.getStaff().toString())));
            }

            entity = payment.makeEntity(p);
        } else {
            Customer customer = customerDao.findById(payment.getCustomer())
                    .orElseThrow(() -> new EntityNotFoundException(payment.getCustomer().toString()));
            Rental rental = rentalDao.findById(payment.getRental())
                    .orElseThrow(() -> new EntityNotFoundException(payment.getRental().toString()));
            Staff staff = staffDao.findById(payment.getStaff())
                    .orElseThrow(() -> new EntityNotFoundException(payment.getStaff().toString()));

            entity = payment.makeEntity(customer, rental, staff);
        }


        return paymentDao.save(entity).makeGenerated();
    }

    public boolean exists(int paymentId) {
        return paymentDao.existsById(paymentId);
    }

    public void delete(int paymentId) {
        paymentDao.deleteById(paymentId);
    }
}
