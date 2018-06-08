package training.database.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import training.database.dao.PaymentDao;
import training.database.entity.PaymentEntity;
import training.generated.CreatePaymentRequest;
import training.generated.Payment;
import training.generated.UpdatePaymentRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentDaoImpl implements PaymentDao {
	protected EntityManager em;
	private Connection connection;
	private static final String baseQuery = "SELECT p FROM sakila.payment p";

    public EntityManager getEm() {
        return em;
    }

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Autowired @Lazy
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Payment> getAll(){
		return convertEntitiesToGenerated(this.em.createQuery(baseQuery, PaymentEntity.class).getResultList());
	}

	@Override
	public Payment getById(long paymentId) {
		return convertEntityToGenerated(this.em.createQuery(baseQuery+" WHERE p.payment_id = '"+paymentId+"'",PaymentEntity.class).getSingleResult());
	}

	@Override
	public List<Payment> getForRental(long rentalId) {
		return convertEntitiesToGenerated(this.em.createQuery(baseQuery+" WHERE p.rental_id = '"+rentalId+"'",PaymentEntity.class).getResultList());
	}

	@Override
	public List<Payment> getFromCustomer(long customerId) {
		return convertEntitiesToGenerated(this.em.createQuery(baseQuery+" WHERE p.customer_id = '"+customerId+"'", PaymentEntity.class).getResultList());
	}

	@Override
	public ResponseEntity<?> insert(CreatePaymentRequest request) {
		Date date = null;
		try {
			date = new Date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getPaymentDate()).getTime());
		} catch (ParseException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date is in wrong format, should be \"yyyy-MM-dd HH:mm:ss\"");
		}
		String sql = "INSERT INTO payment (customer_id, staff_id, rental_id, amount, payment_date) VALUES " +
				"('"+request.getCustomerId()+"', '" +
				request.getStaffId()+"', '" +
				request.getRentalId()+"', '" +
				request.getAmount()+"', '" +
				date.toString().replace("'","")+"');";
		try {
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.ok(getNewestPayment());
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment was not created.\n"+e.getSQLState()+"\n"+e.getLocalizedMessage());
		}
	}

	private Payment getNewestPayment() throws SQLException {
		String sql = "SELECT customer_id, staff_id, rental_id, amount, payment_date, payment_id, payment_date FROM payment ORDER BY payment_id DESC LIMIT 1";

		ResultSet resultSet = connection.createStatement().executeQuery(sql);
		resultSet.next();

		Payment payment = new Payment();

		payment.setAmount(resultSet.getFloat("amount"));
		payment.setCustomerId(resultSet.getLong("customer_id"));
		payment.setPaymentDate(resultSet.getString("payment_date"));
		payment.setPaymentId(resultSet.getLong("payment_id"));
		payment.setRentalId(resultSet.getLong("rental_id"));
		payment.setStaffId(resultSet.getLong("staff_id"));
		payment.setPaymentDate(resultSet.getString("payment_date"));

		return payment;
	}

	@Override
	public ResponseEntity<?> delete(long paymentId) {
		String sql = "DELETE FROM payment WHERE payment_id='"+paymentId+"';";
		try {
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.ok("Payment ["+paymentId+"] was deleted.");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment ["+paymentId+"] was not deleted.\n"+e.getSQLState()+"\n"+e.getLocalizedMessage());
		}
	}

	@Override
	public ResponseEntity<?> update(UpdatePaymentRequest request) {
		String sql = "UPDATE payment SET ";

		if (request.getAmount()!=null && request.getAmount() > 0)
			sql += "amount = '"+request.getAmount()+"', ";
		if (request.getCustomerId()!=null && request.getCustomerId() > 0)
			sql += "customer_id = '"+request.getCustomerId()+"', ";
		if (request.getPaymentDate()!=null && !request.getPaymentDate().isEmpty())
			sql += "payment_date = '"+request.getPaymentDate()+"', ";
		if (request.getRentalId()!=null && request.getRentalId() > 0)
			sql += "rental_id = '"+request.getRentalId()+"', ";
		if (request.getStaffId()!=null && request.getStaffId() > 0)
			sql += "staff_id = '"+request.getStaffId()+"', ";

		sql = sql.substring(0,sql.length()-2) + " WHERE payment_id = '"+request.getPaymentId()+"';";

		try {
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.ok(getById(request.getPaymentId()));
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment ["+request.getPaymentId()+" was not updated.\n"+e.getSQLState()+"\n"+e.getLocalizedMessage());
		}
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

		//System.out.println("payment id = ["+entity.getPayment_id()+"]");
		payment.setPaymentId(entity.getPayment_id());

		//System.out.println("amount = ["+entity.getAmount()+"]");
		payment.setAmount(entity.getAmount());

		//System.out.println("customer id = ["+entity.getCustomer_id()+"]");
		payment.setCustomerId(entity.getCustomer_id());

		//System.out.println("payment date = ["+entity.getPayment_date()+"]");
		payment.setPaymentDate(entity.getPayment_date());

		//System.out.println("rental id = ["+entity.getRental_id()+"]");
		payment.setRentalId(entity.getRental_id());

		//System.out.println("staff id = ["+entity.getStaff_id()+"]");
		payment.setStaffId(entity.getStaff_id());

		return payment;
	}
}
