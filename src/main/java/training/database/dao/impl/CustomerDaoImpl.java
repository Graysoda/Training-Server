package training.database.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import training.database.dao.CustomerDao;
import training.database.entity.CustomerEntity;
import training.generated.CreateCustomerRequest;
import training.generated.Customer;
import training.generated.UpdateCustomerRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class CustomerDaoImpl implements CustomerDao {
	protected EntityManager em;
	private AddressDaoImpl addressDaoImpl;
	private Connection connection;
	private static final String baseQuery = "SELECT c FROM sakila.customer c";

    public EntityManager getEm() {
        return em;
    }

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Autowired @Lazy
    public void setAddressDaoImpl(AddressDaoImpl addressDaoImpl) {
        this.addressDaoImpl = addressDaoImpl;
    }

    @Autowired @Lazy
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

	@Override
    public List<Customer> getActive() {
		return convertEntitysToGenerated(this.em.createQuery(baseQuery+" WHERE c.active = '1'",CustomerEntity.class).getResultList());
	}

	@Override
	public List<Customer> getInactive() {
		return convertEntitysToGenerated(this.em.createQuery(baseQuery+" WHERE c.active = '0'",CustomerEntity.class).setMaxResults(100).getResultList());
	}

	@Override
	public Customer getById(long id) {
		return convertEntityToGenerated(this.em.createQuery(baseQuery+" WHERE c.customer_id = '"+id+"'", CustomerEntity.class).getSingleResult());
	}

	@Override
	public List<Customer> getByStore(long id) {
		return convertEntitysToGenerated(this.em.createQuery(baseQuery+" WHERE c.store_id = '"+id+"'",CustomerEntity.class).getResultList());
	}

	@Override
	public List<Customer> getAll() {
		return convertEntitysToGenerated(this.em.createQuery(baseQuery, CustomerEntity.class).getResultList());
	}

	@Override
	public ResponseEntity<?> insert(CreateCustomerRequest request) {
		String sql = "INSERT INTO customer (store_id, first_name, last_name, email, address_id, active) VALUES " +
				"('"+request.getStoreId()+"', '"+
				request.getFirstName()+"', '"+
				request.getLastName()+"', '"+
				request.getEmail()+"', '"+
				request.getAddressId()+"', '"+
				(request.isActive()?1:0)+"');";
		try {
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.ok(getNewestCustomer());
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Customer was not created.\n"+e.getSQLState()+"\n"+e.getLocalizedMessage());
		}
	}

	private Customer getNewestCustomer() throws SQLException {
		String sql = "SELECT customer_id, store_id, first_name, last_name, email, address_id, active, create_date FROM customer ORDER BY last_update DESC LIMIT 1";

		ResultSet resultSet = connection.createStatement().executeQuery(sql);
		resultSet.next();

		Customer customer = new Customer();

		customer.setCustomerId(resultSet.getLong("customer_id"));
		customer.setEmail(resultSet.getString("email"));
		customer.setFirstName(resultSet.getString("first_name"));
		customer.setLastName(resultSet.getString("last_name"));
		customer.setIsActive(resultSet.getInt("active") == 1);
		customer.setAddress(addressDaoImpl.getById(resultSet.getLong("address_id")));
		customer.setStoreId(resultSet.getLong("store_id"));
		customer.setCreateDate(resultSet.getString("create_date"));

		return customer;
	}

	@Override
	public ResponseEntity<?> delete(long customerId) {
		String sql = "DELETE FROM customer WHERE customer_id='"+customerId+"';";
		try {
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.ok("Customer ["+customerId+"] was deleted");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Customer ["+customerId+"] was not deleted.\n"+e.getSQLState()+"\n"+e.getLocalizedMessage());
		}
	}

	@Override
	public ResponseEntity<?> update(UpdateCustomerRequest request) {
		String sql = "UPDATE customer SET ";

		if (request.isActive()!=null)
			sql += "active = '"+request.isActive()+"', ";
		if (request.getAddressId()!=null)
			sql += "address_id = '"+request.getAddressId()+"', ";
		if (request.getEmail()!=null && !request.getEmail().isEmpty())
			sql += "email = '"+request.getEmail()+"', ";
		if (request.getFirstName()!=null && !request.getFirstName().isEmpty())
			sql += "first_name = '"+request.getFirstName()+"', ";
		if (request.getLastName()!=null && !request.getLastName().isEmpty())
			sql += "last_name = '"+request.getLastName()+"', ";
		if (request.getStoreId()!=null)
			sql += "store_id = '"+request.getStoreId()+"', ";

		sql = sql.subSequence(0,sql.length()-2) + " WHERE customer_id = '"+request.getCustomerId()+"';";

		try {
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.ok(getById(request.getCustomerId()));
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Customer ["+request.getCustomerId()+"] was not updated.\n"+e.getSQLState()+"\n"+e.getLocalizedMessage());
		}
	}

	private List<Customer> convertEntitysToGenerated(List<CustomerEntity> entities){
		List<Customer> customers = new ArrayList<>();

		for (CustomerEntity entity : entities){
			customers.add(convertEntityToGenerated(entity));
		}

		return customers;
	}

	private Customer convertEntityToGenerated(CustomerEntity entity){
		Customer customer = new Customer();

		//System.out.println("customer id = ["+entity.getCustomer_id()+"]");
		customer.setCustomerId(entity.getCustomer_id());

		//System.out.println("store id = ["+entity.getStore_id()+"]");
		customer.setStoreId(entity.getStore_id());

		//System.out.println("first name = ["+entity.getFirst_name()+"]");
		customer.setFirstName(entity.getFirst_name());

		//System.out.println("last name = ["+entity.getLast_name()+"]");
		customer.setLastName(entity.getLast_name());

		//System.out.println("email = ["+entity.getEmail()+"]");
		customer.setEmail(entity.getEmail());

		//System.out.println("address id = ["+entity.getAddress_id()+"]");
		customer.setAddress(addressDaoImpl.getById(entity.getAddress_id()));

		//System.out.println("active = ["+entity.isActive()+"]");
		customer.setIsActive(entity.isActive());

		//System.out.println("insert date = ["+entity.getCreate_date()+"]");
		customer.setCreateDate(entity.getCreate_date());

		return customer;
	}
}
