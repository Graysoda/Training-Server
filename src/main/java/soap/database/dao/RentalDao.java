package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.Database;
import soap.database.entity.RentalEntity;
import soap.generated.CreateRentalRequest;
import soap.generated.Rental;
import soap.generated.UpdateRentalRequest;

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
public class RentalDao extends Database {
	@PersistenceContext
	private EntityManager em;
	@Autowired private CustomerDao customerDao;
	@Autowired private FilmDao filmDao;
	@Autowired private StaffDao staffDao;
	private String baseQuery = "SELECT r FROM sakila.rental r ";

	public Rental getById(long id) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<RentalEntity> query = criteriaBuilder.createQuery(RentalEntity.class);
		Root<RentalEntity> root = query.from(RentalEntity.class);
		query.where(criteriaBuilder.equal(root.get("rental_id"),id));
		query.multiselect(makeSelection(root));
		return convertEntityToGenerated(this.em.createQuery(query).getSingleResult());
	}

	private List<Selection<?>> makeSelection(Root<RentalEntity> root) {
		List<Selection<?>> selections = new ArrayList<>();

		selections.add(root.get("rental_id"));
		selections.add(root.get("customer_id"));
		selections.add(root.get("staff_id"));
		selections.add(root.get("inventory_id"));
		selections.add(root.get("rental_date"));
		selections.add(root.get("return_date"));
		selections.add(root.get("last_update"));

		return selections;
	}

	public List<Rental> getByCustomerId(long id) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<RentalEntity> query = criteriaBuilder.createQuery(RentalEntity.class);
		Root<RentalEntity> root = query.from(RentalEntity.class);
		query.where(criteriaBuilder.equal(root.get("customer_id"),id));
		return convertEntitiesToGenerated(this.em.createQuery(query).getResultList());
	}

	public List<Rental> getByStaffId(long id) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<RentalEntity> query = criteriaBuilder.createQuery(RentalEntity.class);
		Root<RentalEntity> root = query.from(RentalEntity.class);
		query.where(criteriaBuilder.equal(root.get("staff_id"),id));
		return convertEntitiesToGenerated(this.em.createQuery(query).getResultList());
	}

	public List<Rental> getByStartDate(String date) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<RentalEntity> query = criteriaBuilder.createQuery(RentalEntity.class);
		Root<RentalEntity> root = query.from(RentalEntity.class);
		query.where(criteriaBuilder.equal(root.get("rental_date"),date));
		return convertEntitiesToGenerated(this.em.createQuery(query).getResultList());
	}

	public List<Rental> getByReturnDate(String date) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<RentalEntity> query = criteriaBuilder.createQuery(RentalEntity.class);
		Root<RentalEntity> root = query.from(RentalEntity.class);
		query.where(criteriaBuilder.equal(root.get("return_date"),date));
		return convertEntitiesToGenerated(this.em.createQuery(query).getResultList());
	}

	public void insert(CreateRentalRequest request) {
		String sql = "INSERT INTO rental (customer_id, staff_id, inventory_id, rental_date, return_date) VALUES " +
				"("+request.getCustomerId()+", "+
				request.getStaffId()+", "+
				request.getInventoryId()+", "+
				request.getRentalDate()+", "+
				request.getReturnDate()+");";
		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(long rentalId) {
		String sql = "DELETE FROM rental WHERE rental_id='"+rentalId+"';";
		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(UpdateRentalRequest request) {
		String sql = "UPDATE rental SET ";

		if (request.getCustomerId()!=null)
			sql += "customer_id = '"+request.getCustomerId()+"', ";
		if (request.getInventoryId()!=null)
			sql += "inventory_id = '"+request.getInventoryId()+"', ";
		if (request.getRentalDate()!=null)
			sql += "rental_date = '"+request.getRentalDate()+"', ";
		if (request.getReturnDate()!=null)
			sql += "return_date = '"+request.getReturnDate()+"', ";
		if (request.getStaffId()!=null)
			sql += "staff_id = '"+request.getStaffId()+"', ";

		sql = sql.substring(0,sql.length()-3) + "WHERE rental_id = '"+request.getRentalId()+"';";

		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Rental convertEntityToGenerated(RentalEntity entity) {
		Rental rental = new Rental();

		rental.setRentalId(entity.getRental_id());
		rental.setCustomer(customerDao.getById(entity.getCustomer_id()));
		rental.setStaff(staffDao.getById(entity.getStaff_id()));
		rental.setItem(filmDao.getById(entity.getInventory_id()));
		rental.setRentalDate(entity.getRental_date());
		rental.setReturnDate(entity.getReturn_date());

		return rental;
	}

	private List<Rental> convertEntitiesToGenerated(List<RentalEntity> entities) {
		List<Rental> rentals = new ArrayList<>(entities.size());

		for (RentalEntity entity : entities) {
			rentals.add(convertEntityToGenerated(entity));
		}

		return rentals;
	}
}
