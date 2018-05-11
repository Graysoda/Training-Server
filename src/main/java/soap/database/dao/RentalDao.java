package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import soap.database.Database;
import soap.database.entity.RentalEntity;
import soap.generated.CreateRentalRequest;
import soap.generated.Rental;
import soap.generated.UpdateRentalRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RentalDao extends Database {
	@PersistenceContext @Lazy private EntityManager em;
	@Autowired @Lazy private CustomerDao customerDao;
	@Autowired @Lazy private FilmDao filmDao;
	@Autowired @Lazy private StaffDao staffDao;
	private static final String baseQuery = "SELECT r FROM sakila.rental r";

	public Rental getById(long id) {
		return convertEntityToGenerated(this.em.createQuery(baseQuery+" WHERE r.rental_id = '"+id+"'",RentalEntity.class).getSingleResult());
	}

	public List<Rental> getByCustomerId(long id) {
		return convertEntitiesToGenerated(this.em.createQuery(baseQuery+" WHERE r.customer_id = '"+id+"'",RentalEntity.class).getResultList());
	}

	public List<Rental> getByStaffId(long id) {
		return convertEntitiesToGenerated(this.em.createQuery(baseQuery+" WHERE r.staff_id = '"+id+"'", RentalEntity.class).getResultList());
	}

	public List<Rental> getByStartDate(String date) {
	    String query = baseQuery + " WHERE r.rental_id " +
                "IN " +
				"(SELECT r.rental_id " +
                "FROM sakila.rental r " +
                "WHERE DATE_TRUNC('day', r.return_date) >= TO_DATE('"+date+"', 'YYYY-MM-DD') " +
                "AND DATE_TRUNC('day', r.return_date) < (TO_DATE('"+date+"', 'YYYY-MM-DD') + MAKE_INTERVAL(days => 1)))";
		return convertEntitiesToGenerated(this.em.createQuery(query,RentalEntity.class).getResultList());
	}

	public List<Rental> getByReturnDate(String date) {
        String query = baseQuery + " WHERE r.rental_id " +
				"IN " +
				"(SELECT r.rental_id " +
				"FROM sakila.rental r " +
				"WHERE DATE_TRUNC('day', r.return_date) >= TO_DATE('"+date+"', 'YYYY-MM-DD') " +
				"AND DATE_TRUNC('day', r.return_date) < (TO_DATE('"+date+"', 'YYYY-MM-DD') + CAST('1 DAY' AS INTERVAL)))";
        return convertEntitiesToGenerated(this.em.createQuery(query, RentalEntity.class).getResultList());
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

		sql = sql.substring(0,sql.length()-2) + " WHERE rental_id = '"+request.getRentalId()+"';";

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
