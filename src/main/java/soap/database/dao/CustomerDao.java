package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.Database;
import soap.database.entity.CustomerEntity;
import soap.generated.CreateCustomerRequest;
import soap.generated.Customer;
import soap.generated.UpdateCustomerRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class CustomerDao extends Database {
	@PersistenceContext
	private EntityManager em;
	@Autowired private AddressDao addressDao;
	private String baseQuery = "SELECT c from sakila.customer c ";

	public CustomerDao(){}

	public List<Customer> getActive() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<CustomerEntity> query = criteriaBuilder.createQuery(CustomerEntity.class);
		Root<CustomerEntity> root = query.from(CustomerEntity.class);
		query.where(criteriaBuilder.equal(root.get("active"),1));
		query.multiselect(makeSelection(root));

		return convertEntitystoGenerated(this.em.createQuery(query).setMaxResults(100).getResultList());
	}

	private List<Selection<?>> makeSelection(Root<CustomerEntity> root) {
		List<Selection<?>> selections = new ArrayList<>();
		selections.add(root.get("customer_id"));
		selections.add(root.get("store_id"));
		selections.add(root.get("first_name"));
		selections.add(root.get("last_name"));
		selections.add(root.get("email"));
		selections.add(root.get("address_id"));
		selections.add(root.get("active"));
		selections.add(root.get("create_date"));
		selections.add(root.get("last_update"));

		return selections;
	}

	public Customer getById(long id) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<CustomerEntity> query = criteriaBuilder.createQuery(CustomerEntity.class);
		Root<CustomerEntity> root = query.from(CustomerEntity.class);
		query.where(criteriaBuilder.equal(root.get("customer_id"),id));

		return convertEntityToGenerated(this.em.createQuery(query).getSingleResult());
	}

	public List<Customer> getByStore(long id) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<CustomerEntity> query = criteriaBuilder.createQuery(CustomerEntity.class);
		Root<CustomerEntity> root = query.from(CustomerEntity.class);
		query.where(criteriaBuilder.equal(root.get("store_id"),id));

		return convertEntitystoGenerated(this.em.createQuery(query).setMaxResults(100).getResultList());
	}

	public List<Customer> getAll() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<CustomerEntity> query = criteriaBuilder.createQuery(CustomerEntity.class);
		query.multiselect(makeSelection(query.from(CustomerEntity.class)));

		return convertEntitystoGenerated(this.em.createQuery(query).setMaxResults(100).getResultList());
	}

	public void insert(CreateCustomerRequest request) {
		String sql = "INSERT INTO customer (store_id, first_name, last_name, email, address_id, active) VALUES " +
				"('"+request.getStoreId()+"', '"+
				request.getFirstName()+"', '"+
				request.getLastName()+"', '"+
				request.getEmail()+"', '"+
				request.getAddressId()+"', '"+
				request.isActive()+"');";
		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(long customerId) {
		String sql = "DELETE FROM customer WHERE customer_id='"+customerId+"';";
		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(UpdateCustomerRequest request) {
		String sql = "UPDATE customer SET ";
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
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private List<Customer> convertEntitystoGenerated(List<CustomerEntity> entities){
		List<Customer> customers = new ArrayList<>();

		for (CustomerEntity entity : entities){
			customers.add(convertEntityToGenerated(entity));
		}

		return customers;
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
}
