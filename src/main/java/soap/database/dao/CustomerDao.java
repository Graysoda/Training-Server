package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.entity.CustomerEntity;
import soap.generated.CreateCustomerRequest;
import soap.generated.Customer;
import soap.generated.UpdateCustomerRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class CustomerDao {
	@PersistenceContext
	private EntityManager em;
	private Connection connection;
	private AddressDao addressDao;
	private String baseQuery = "SELECT c from sakila.customer c ";

	public CustomerDao(){}

	@Autowired
	public void setAddressDao(AddressDao addressDao) {
		this.addressDao = addressDao;
	}

	@Autowired
	public void setConnection(Connection connection){
		this.connection = connection;
	}

	public void insert(CreateCustomerRequest request) {
		String sql = "INSERT INTO sakila.customer (store_id, first_name, last_name, email, address_id, active) VALUES " +
				"('"+request.getStoreId()+"', '"+
				request.getFirstName()+"', '"+
				request.getLastName()+"', '"+
				request.getEmail()+"', '"+
				request.getAddressId()+"', '"+
				request.isActive()+"');";
		try {
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Customer> getActive() {
		return convertEntitystoGenerated(this.em.createQuery(baseQuery+"WHERE c.active='true'",CustomerEntity.class).getResultList());
	}

	public Customer getById(long id) {
		return convertEntityToGenerated(this.em.createQuery(baseQuery+"WHERE c.customer_id='"+id+"'",CustomerEntity.class).getSingleResult());
	}

	public List<Customer> getByStore(long id) {
		return convertEntitystoGenerated(this.em.createQuery(baseQuery+"WHERE c.store_id='"+id+"'",CustomerEntity.class).getResultList());
	}

	private List<Customer> convertEntitystoGenerated(List<CustomerEntity> entities){
		List<Customer> customers = new ArrayList<>();

		for (CustomerEntity entity : entities){
			customers.add(convertEntityToGenerated(entity));
		}

		return customers;
	}

	public List<Customer> getAll() {
		return convertEntitystoGenerated(this.em.createQuery(baseQuery,CustomerEntity.class).getResultList());
	}

	private Customer convertEntityToGenerated(CustomerEntity entity){
		Customer customer = new Customer();

		customer.setStoreId(entity.getStore_id());
		customer.setFirstName(entity.getFirst_name());
		customer.setLastName(entity.getLast_name());
		customer.setEmail(entity.getEmail());
		customer.setAddress(addressDao.getById(entity.getAddress_id()));
		customer.setIsActive(entity.isActive());
		customer.setCreateDate(entity.getCreate_date());
		customer.setLastUpdate(entity.getLast_update());

		return customer;
	}

	public void delete(long customerId) {
		String sql = "DELETE FROM sakila.customer WHERE customer_id='"+customerId+"';";
		try {
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(UpdateCustomerRequest request) {
		String sql = "UPDATE sakila.customer SET ";
		String values = "";

		if (request.isActive()!=null)
			values += "active = '"+request.isActive()+"', ";
		if (request.getAddressId()!=null)
			values += "address_id = '"+request.getAddressId()+"', ";
		if (request.getEmail()!=null)
			values += "email = '"+request.getEmail()+"', ";
		if (request.getFirstName()!=null)
			values += "first_name = '"+request.getFirstName()+"', ";
		if (request.getLastName()!=null)
			values += "last_name = '"+request.getLastName()+"', ";
		if (request.getStoreId()!=null)
			values += "store_id = '"+request.getStoreId()+"', ";

		values = (String) values.subSequence(0,values.length()-3);

		sql += values + " WHERE customer_id = '"+request.getCustomerId()+"';";

		try {
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
