package training.database.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import training.database.dao.StaffDao;
import training.database.entity.StaffEntity;
import training.generated.CreateStaffRequest;
import training.generated.Staff;
import training.generated.UpdateStaffRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StaffDaoImpl implements StaffDao {
	protected EntityManager em;
	private AddressDaoImpl addressDaoImpl;
	private Connection connection;
	private static final String baseQuery = "SELECT s FROM sakila.staff s";

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

	public Staff getById(long id) {
		return convertEntityToGenerated(this.em.createQuery(baseQuery+" WHERE s.staff_id = '"+id+"'",StaffEntity.class).getSingleResult());
	}

	public List<Staff> getAll() {
		return convertEntitiesToGenerated(this.em.createQuery(baseQuery, StaffEntity.class).getResultList());
	}

	public ResponseEntity<?> insert(CreateStaffRequest request) {
		String sql = "INSERT INTO staff (first_name, last_name, address_id, email, store_id, active, username, password) VALUES " +
				"('"+request.getFirstName()+"', '" +
				request.getLastName()+"', '" +
				request.getAddressId()+"', '" +
				request.getEmail()+"', '" +
				request.getStoreId()+"', '" +
				(request.isActive()?1:0)+"', '" +
				request.getUsername()+"', '" +
				request.getPassword()+"');";
		try {
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.ok(getNewestStaff());
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Staff was not created.\n"+e.getSQLState()+"\n"+e.getLocalizedMessage());
		}
	}

	private Staff getNewestStaff() throws SQLException {
		String sql = "SELECT staff_id, first_name, last_name, address_id, email, store_id, active, username, password from staff ORDER BY last_update DESC LIMIT 1";

		ResultSet resultSet = connection.createStatement().executeQuery(sql);
		resultSet.next();

		Staff staff = new Staff();

		staff.setStaffId(resultSet.getLong("staff_id"));
		staff.setAddress(addressDaoImpl.getById(resultSet.getLong("address_id")));
		staff.setEmail(resultSet.getString("email"));
		staff.setFirstName(resultSet.getString("first_name"));
		staff.setIsActive(resultSet.getBoolean("active"));
		staff.setLastName(resultSet.getString("last_name"));
		staff.setPassword(resultSet.getString("password"));
		staff.setUsername(resultSet.getString("username"));
		staff.setStoreId(resultSet.getLong("store_id"));

		return staff;
	}

	public ResponseEntity<?> delete(long staffId) {
		String sql = "DELETE FROM staff WHERE staff_id='"+staffId+"';";
		try {
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.ok("Staff ["+staffId+"] was deleted.");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Staff ["+staffId+"] was not deleted.\n"+e.getSQLState()+"\n"+e.getLocalizedMessage());
		}
	}

	public ResponseEntity<?> update(UpdateStaffRequest request) {
		String sql = "UPDATE staff SET ";

		if (request.isActive()!=null)
			sql += "active = '"+(request.isActive()?1:0)+"', ";
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

		sql = sql.substring(0,sql.length()-2) + " WHERE staff_id = '"+request.getStaffId()+"';";

		try {
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.ok(getById(request.getStaffId()));
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Staff ["+request.getStaffId()+"] was not updated.\n"+e.getSQLState()+"\n"+e.getLocalizedMessage());
		}
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

		//System.out.println("staff id = ["+entity.getStaff_id()+"]");
		staff.setStaffId(entity.getStaff_id());

		//System.out.println("first name = ["+entity.getFirst_name()+"]");
		staff.setFirstName(entity.getFirst_name());

		//System.out.println("last name = ["+entity.getLast_name()+"]");
		staff.setLastName(entity.getLast_name());

		//System.out.println("email  =["+entity.getEmail()+"]");
		staff.setEmail(entity.getEmail());

		//System.out.println("address id = ["+entity.getAddress_id()+"]");
		staff.setAddress(addressDaoImpl.getById(entity.getAddress_id()));

		//System.out.println("store id = ["+entity.getStore_id()+"]");
		staff.setStoreId(entity.getStore_id());

		//System.out.println("active = ["+entity.isActive()+"]");
		staff.setIsActive(entity.isActive());

		//System.out.println("username = ["+entity.getUsername()+"]");
		staff.setUsername(entity.getUsername());

		//System.out.println("password = ["+entity.getPassword()+"]");
		staff.setPassword(entity.getPassword());

		return staff;
	}
}
