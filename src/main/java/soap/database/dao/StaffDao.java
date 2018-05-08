package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.Database;
import soap.database.entity.StaffEntity;
import soap.generated.CreateStaffRequest;
import soap.generated.Staff;
import soap.generated.UpdateStaffRequest;

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
public class StaffDao extends Database {
	@PersistenceContext
	private EntityManager em;
	@Autowired private AddressDao addressDao;

	public Staff getById(long id) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<StaffEntity> query = criteriaBuilder.createQuery(StaffEntity.class);
		Root<StaffEntity> root = query.from(StaffEntity.class);
		query.where(criteriaBuilder.equal(root.get("staff_id"),id));
		query.multiselect(makeSelection(root));

		return convertEntityToGenerated(this.em.createQuery(query).getSingleResult());
	}

	private List<Selection<?>> makeSelection(Root<StaffEntity> root) {
		List<Selection<?>> selections = new ArrayList<>();

		selections.add(root.get("staff_id"));
		selections.add(root.get("first_name"));
		selections.add(root.get("last_name"));
		selections.add(root.get("address_id"));
		selections.add(root.get("email"));
		selections.add(root.get("store_id"));
		selections.add(root.get("active"));
		selections.add(root.get("username"));
		selections.add(root.get("password"));
		selections.add(root.get("last_update"));

		return selections;
	}

	public List<Staff> getAll() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<StaffEntity> query = criteriaBuilder.createQuery(StaffEntity.class);
		query.multiselect(makeSelection(query.from(StaffEntity.class)));
		return convertEntitiesToGenerated(this.em.createQuery(query).getResultList());
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
		String sql = "INSERT INTO staff (staff.first_name, staff.last_name, staff.address_id, staff.email, staff.store_id, staff.active, staff.username, staff.password) VALUES " +
				"("+request.getFirstName()+", " +
				request.getLastName()+", " +
				request.getAddressId()+", " +
				request.getEmail()+", " +
				request.getStoreId()+", " +
				request.isActive()+", " +
				request.getUsername()+", " +
				request.getPassword()+");";
		try {
			getConnection().createStatement().executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(long staffId) {
		String sql = "DELETE FROM staff WHERE staff.staff_id='"+staffId+"';";
		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(UpdateStaffRequest request) {
		String sql = "UPDATE staff SET ";

		if (request.isActive()!=null)
			sql += "staff.active = '"+request.isActive()+"', ";
		if (request.getAddressId()!=null)
			sql += "staff.address_id = '"+request.getAddressId()+"', ";
		if (request.getEmail()!=null)
			sql += "staff.email = '"+request.getEmail()+"', ";
		if (request.getFirstName()!=null)
			sql += "staff.first_name = '"+request.getFirstName()+"', ";
		if (request.getLastName()!=null)
			sql += "staff.last_name = '"+request.getLastName()+"', ";
		if (request.getPassword()!=null)
			sql += "staff.password = '"+request.getPassword()+"', ";
		if (request.getStoreId()!=null)
			sql += "staff.store_id  ='"+request.getStoreId()+"', ";
		if (request.getUsername()!=null)
			sql += "staff.username = '"+request.getUsername()+"', ";

		sql = sql.substring(0,sql.length()-3) + " WHERE staff.staff_id = '"+request.getStaffId()+"';";

		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
