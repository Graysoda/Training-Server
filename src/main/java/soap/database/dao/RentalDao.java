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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RentalDao extends Database {
	@PersistenceContext
	private EntityManager em;
	private CustomerDao customerDao;
	private FilmDao filmDao;
	private StaffDao staffDao;
	private String baseQuery = "SELECT r FROM sakila.rental r ";

	@Autowired
	public void setStaffDao(StaffDao staffDao) {
		this.staffDao = staffDao;
	}

	@Autowired
	public void setFilmDao(FilmDao filmDao) {
		this.filmDao = filmDao;
	}

	@Autowired
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	private Rental convertEntityToGenerated(RentalEntity entity) throws SQLException {
		Rental rental = new Rental();

		rental.setRentalId(entity.getRental_id());
		rental.setCustomer(customerDao.getById(entity.getCustomer_id()));
		rental.setStaff(staffDao.getById(entity.getStaff_id()));
		rental.setItem(filmDao.getById(entity.getInventory_id()));
		rental.setRentalDate(entity.getRental_date());
		rental.setReturnDate(entity.getReturn_date());

		return rental;
	}

	private List<Rental> convertEntitiesToGenerated(List<RentalEntity> entities) throws SQLException {
		List<Rental> rentals = new ArrayList<>(entities.size());

		for (RentalEntity entity : entities) {
			rentals.add(convertEntityToGenerated(entity));
		}

		return rentals;
	}

	public Rental getById(long id) throws SQLException {
		return convertEntityToGenerated(this.em.createQuery(baseQuery+"WHERE r.rental_id='"+id+"'",RentalEntity.class).getSingleResult());
	}



	public List<Rental> getByCustomerId(long id) throws SQLException {
		return convertEntitiesToGenerated(this.em.createQuery(baseQuery+"WHERE r.customer_id='"+id+"'",RentalEntity.class).getResultList());
	}

	public List<Rental> getByStaffId(long id) throws SQLException {
		return convertEntitiesToGenerated(this.em.createQuery(baseQuery+"WHERE r.staff_id='"+id+"'",RentalEntity.class).getResultList());
	}

	public List<Rental> getByStartDate(String date) throws SQLException {
		return convertEntitiesToGenerated(this.em.createQuery(baseQuery+"WHERE r.rental_date='"+date+"'",RentalEntity.class).getResultList());
	}

	public List<Rental> getByReturnDate(String date) throws SQLException {
		return convertEntitiesToGenerated(this.em.createQuery(baseQuery+"WHERE r.return_date='"+date+"'",RentalEntity.class).getResultList());
	}

	public void insert(CreateRentalRequest request) {
		String sql = "INSERT INTO sakila.rental (customer_id, staff_id, inventory_id, rental_date, return_date) VALUES " +
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
		String sql = "DELETE FROM sakila.rental WHERE rental_id='"+rentalId+"';";
		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(UpdateRentalRequest request) {
		String sql = "UPDATE sakila.rental SET ";

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
}
