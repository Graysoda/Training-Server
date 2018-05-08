package soap.database.dao;

import org.springframework.stereotype.Repository;
import soap.database.Database;
import soap.database.entity.PaymentEntity;
import soap.generated.CreatePaymentRequest;
import soap.generated.Payment;
import soap.generated.UpdatePaymentRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentDao extends Database {
	@PersistenceContext
	private EntityManager em;
	private String baseQuery = "SELECT p FROM sakila.payment p ";

	public List<Payment> getAll(){
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<PaymentEntity> query = criteriaBuilder.createQuery(PaymentEntity.class);

		return convertEntitiesToGenerated(this.em.createQuery(query).getResultList());
	}

	private List<Payment> convertEntitiesToGenerated(List<PaymentEntity> entities){
		List<Payment> payments = new ArrayList<>(entities.size());

		for (PaymentEntity entity : entities) {
			payments.add(convertEntityToGenerated(entity));
		}

		return payments;
	}

	private Payment convertEntityToGenerated(PaymentEntity entity){
		Payment payment = new Payment();

		payment.setPaymentId(entity.getPayment_id());
		payment.setAmount(entity.getAmount());
		payment.setCustomerId(entity.getCustomer_id());
		payment.setPaymentDate(entity.getPayment_date());
		payment.setRentalId(entity.getRental_id());
		payment.setStaffId(entity.getStaff_id());

		return payment;
	}

	public void insert(CreatePaymentRequest request) {
		String sql = "INSERT INTO payment (payment.customer_id, payment.staff_id, payment.rental_id, payment.amount, payment.payment_date) VALUES " +
				"("+request.getCustomerId()+", " +
				request.getStaffId()+", " +
				request.getRentalId()+", " +
				request.getAmount()+", " +
				request.getPaymentDate()+");";
		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(long paymentId) {
		String sql = "DELETE FROM payment WHERE payment.payment_id='"+paymentId+"';";
		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(UpdatePaymentRequest request) {
		String sql = "UPDATE payment SET ";

		if (request.getAmount()!=null)
			sql += "payment.amount = '"+request.getAmount()+"', ";
		if (request.getCustomerId()!=null)
			sql += "payment.customer_id = '"+request.getCustomerId()+"', ";
		if (request.getPaymentDate()!=null)
			sql += "payment.payment_date = '"+request.getPaymentDate()+"', ";
		if (request.getRentalId()!=null)
			sql += "payment.rental_id = '"+request.getRentalId()+"', ";
		if (request.getStaffId()!=null)
			sql += "payment.staff_id = '"+request.getStaffId()+"', ";

		sql = sql.substring(0,sql.length()-3) + " WHERE payment.payment_id = '"+request.getPaymentId()+"';";

		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
