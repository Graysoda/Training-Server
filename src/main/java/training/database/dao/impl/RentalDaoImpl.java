package training.database.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import training.database.dao.RentalDao;
import training.database.entity.RentalEntity;
import training.generated.CreateRentalRequest;
import training.generated.Rental;
import training.generated.UpdateRentalRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Repository
public class RentalDaoImpl implements RentalDao {
	protected EntityManager em;
	private CustomerDaoImpl customerDaoImpl;
	private FilmDaoImpl filmDaoImpl;
	private StaffDaoImpl staffDaoImpl;
	private Connection connection;
	private static final String baseQuery = "SELECT r FROM sakila.rental r";

    public EntityManager getEm() {
        return em;
    }

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Autowired @Lazy
    public void setFilmDaoImpl(FilmDaoImpl filmDaoImpl) {
        this.filmDaoImpl = filmDaoImpl;
    }

    @Autowired @Lazy
    public void setCustomerDaoImpl(CustomerDaoImpl customerDaoImpl) {
        this.customerDaoImpl = customerDaoImpl;
    }

    @Autowired @Lazy
    public void setStaffDaoImpl(StaffDaoImpl staffDaoImpl) {
        this.staffDaoImpl = staffDaoImpl;
    }

    @Autowired @Lazy
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public List<Rental> getAll() {
		return convertEntitiesToGenerated(this.em.createQuery(baseQuery,RentalEntity.class).getResultList());
	}

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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
			throw new java.lang.Error("wrong date format, should be 'yyyy-MM-dd'");
		}
		calendar.add(Calendar.DATE,1);
		String query = baseQuery + " WHERE r.rental_id " +
                "IN " +
				"(SELECT r.rental_id " +
                "FROM sakila.rental r " +
                "WHERE DATE_TRUNC('day', r.return_date) >= TO_DATE('"+date+"', 'YYYY-MM-DD') " +
                "AND DATE_TRUNC('day', r.return_date) < (TO_DATE('"+sdf.format(calendar.getTime())+"', 'YYYY-MM-DD')))";
		return convertEntitiesToGenerated(this.em.createQuery(query,RentalEntity.class).getResultList());
	}

	public List<Rental> getByReturnDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
			throw new java.lang.Error("wrong date format, should be 'yyyy-MM-dd'");
		}
		calendar.add(Calendar.DATE,1);
        String query = baseQuery + " WHERE r.rental_id " +
				"IN " +
				"(SELECT r.rental_id " +
				"FROM sakila.rental r " +
				"WHERE DATE_TRUNC('day', r.return_date) >= TO_DATE('"+date+"', 'YYYY-MM-DD') " +
				"AND DATE_TRUNC('day', r.return_date) < (TO_DATE('"+sdf.format(calendar.getTime())+"', 'YYYY-MM-DD')))";
        return convertEntitiesToGenerated(this.em.createQuery(query, RentalEntity.class).getResultList());
	}

	public ResponseEntity<?> insert(CreateRentalRequest request) {
		String sql = "INSERT INTO rental (customer_id, staff_id, inventory_id, rental_date, return_date) VALUES " +
				"("+request.getCustomerId()+", "+
				request.getStaffId()+", "+
				request.getInventoryId()+", "+
				request.getRentalDate()+", "+
				request.getReturnDate()+");";
		try {
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.ok("Rental created.");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Rental was not created.\n"+e.getSQLState()+"\n"+e.getLocalizedMessage());
		}
	}

	public ResponseEntity<?> delete(long rentalId) {
		String sql = "DELETE FROM rental WHERE rental_id='"+rentalId+"';";
		try {
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.ok("Rental ["+rentalId+"] was deleted.");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Rental ["+rentalId+"] was not deleted.\n"+e.getSQLState()+"\n"+e.getLocalizedMessage());
		}
	}

	public ResponseEntity<?> update(UpdateRentalRequest request) {
		String sql = "UPDATE rental SET ";

		if (request.getCustomerId()!=null && request.getCustomerId() > 0)
			sql += "customer_id = '"+request.getCustomerId()+"', ";
		if (request.getInventoryId()!=null && request.getInventoryId() > 0)
			sql += "inventory_id = '"+request.getInventoryId()+"', ";
		if (request.getRentalDate()!=null && !request.getRentalDate().isEmpty())
			sql += "rental_date = '"+request.getRentalDate()+"', ";
		if (request.getReturnDate()!=null && !request.getReturnDate().isEmpty())
			sql += "return_date = '"+request.getReturnDate()+"', ";
		if (request.getStaffId()!=null && request.getStaffId() > 0)
			sql += "staff_id = '"+request.getStaffId()+"', ";

		sql = sql.substring(0,sql.length()-2) + " WHERE rental_id = '"+request.getRentalId()+"';";

		try {
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.ok("Rental ["+request.getRentalId()+"] was updated.");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Rental ["+request.getRentalId()+"] was not updated.\n"+e.getSQLState()+"\n"+e.getLocalizedMessage());
		}
	}

	private Rental convertEntityToGenerated(RentalEntity entity) {
		Rental rental = new Rental();

		//System.out.println("rental id = ["+entity.getRental_id()+"]");
		rental.setRentalId(entity.getRental_id());

		//System.out.println("customer id = ["+entity.getCustomer_id()+"]");
		rental.setCustomer(customerDaoImpl.getById(entity.getCustomer_id()));

		//System.out.println("staff id = ["+entity.getStaff_id()+"]");
		rental.setStaff(staffDaoImpl.getById(entity.getStaff_id()));

		//System.out.println("inventory id = ["+entity.getInventory_id()+"]");
		rental.setItem(filmDaoImpl.getById(entity.getInventory_id()));

		//System.out.println("rental date = ["+entity.getRental_date()+"]");
		rental.setRentalDate(entity.getRental_date());

		//System.out.println("return date = ["+entity.getReturn_date()+"]");
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
