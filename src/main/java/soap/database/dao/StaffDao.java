package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.entity.StaffEntity;
import soap.generated.CreateStaffRequest;
import soap.generated.Staff;
import soap.generated.UpdateStaffRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StaffDao {
	@PersistenceContext
	private EntityManager em;
	private Connection connection;
	private AddressDao addressDao;
	private String baseQuery = "SELECT s FROM sakila.staff s ";

	@Autowired
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Autowired
	public void setAddressDao(AddressDao addressDao) {
		this.addressDao = addressDao;
	}

	public Staff getById(long id) {
		return convertEntityToGenerated(this.em.createQuery(baseQuery+"WHERE s.staff_id='"+id+"'",StaffEntity.class).getSingleResult());
	}

	public List<Staff> getAll() {
		return convertEntitiesToGenerated(this.em.createQuery(baseQuery,StaffEntity.class).getResultList());
	}

	private List<Staff> convertEntitiesToGenerated(List<StaffEntity> entities) {
		List<Staff> staff = new ArrayList<>(entities.size());

		for (StaffEntity entity : entities) {
			staff.add(convertEntityToGenerated(entity));
		}

		return staff;
	}

	private Staff convertEntityToGenerated(StaffEntity entity){
		Staff staff = new Staff();

		staff.setStaffId(entity.getStaff_id());
		staff.setFirstName(entity.getFirst_name());
		staff.setLastName(entity.getLast_name());
		staff.setEmail(entity.getEmail());
		staff.setAddress(addressDao.getById(entity.getAddress_id()));
		staff.setStoreId(entity.getStore_id());
		staff.setIsActive(entity.isActive());
		staff.setUsername(entity.getUsername());
		staff.setPassword(entity.getPassword());
		staff.setLastUpdate(entity.getLast_update());

		return staff;
	}


	public void insert(CreateStaffRequest request) {
		String sql = "INSERT INTO (first_name, last_name, address_id, email, store_id, active, username, password) VALUES " +
				"("+request.getFirstName()+", " +
				request.getLastName()+", " +
				request.getAddressId()+", " +
				request.getEmail()+", " +
				request.getStoreId()+", " +
				request.isActive()+", " +
				request.getUsername()+", " +
				request.getPassword()+");";
		try {
			connection.createStatement().executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(long staffId) {
		String sql = "DELETE FROM sakila.staff WHERE satff_id='"+staffId+"';";
		try {
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(UpdateStaffRequest request) {
		String sql = "UPDATE sakila.staff SET ";

		if (request.isActive()!=null)
			sql += "active = '"+request.isActive()+"', ";
		if (request.getAddressId()!=null)
			sql += "address_id = '"+request.getAddressId()+"', ";
		if (request.getEmail()!=null)
			sql += "email = '"+request.getEmail()+"', ";
		if (request.getFirstName()!=null)
			sql += "first_name = '"+request.getFirstName()+"', ";
		if (request.getLastName()!=null)
			sql += "last_name = '"+request.getLastName()+"', ";
		if (request.getPassword()!=null)
			sql += "password = '"+request.getPassword()+"', ";
		if (request.getStoreId()!=null)
			sql += "store_id  ='"+request.getStoreId()+"', ";
		if (request.getUsername()!=null)
			sql += "username = '"+request.getUsername()+"', ";

		sql = sql.substring(0,sql.length()-3) + " WHERE staff_id = '"+request.getStaffId()+"';";

		try {
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
