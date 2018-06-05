package training.database.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import training.database.dao.AddressDao;
import training.database.entity.AddressEntity;
import training.generated.Address;
import training.generated.CreateAddressRequest;
import training.generated.UpdateAddressRequest;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AddressDaoImpl implements AddressDao {
	protected EntityManager em;
	@Autowired @Lazy
	private CityDaoImpl cityDaoImpl;
	@Lazy @Autowired
	private Connection connection;
	private static final String baseQuery = "SELECT adr from sakila.address adr";

	public EntityManager getEm(){
		return em;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager){
		this.em = entityManager;
	}

	@Override
	public Address getById(long id){
		return convertEntityToGenerated(this.em.createQuery(baseQuery+" WHERE adr.address_id = '"+id+"'",AddressEntity.class).getSingleResult());
	}

	@Override
	public List<Address> getByCity(String city) {
		return convertEntitiesToGenerate(this.em.createQuery(baseQuery+" WHERE adr.city_id = '"+cityDaoImpl.getIdByName(city)+"'", AddressEntity.class).getResultList());
	}

	@Override
	public List<Address> getByPostalCode(String postalCode) {
		return convertEntitiesToGenerate(this.em.createQuery(baseQuery+" where adr.postal_code = '"+postalCode+"'", AddressEntity.class).getResultList());
	}

	@Override
	public List<Address> getAll() {
		return convertEntitiesToGenerate(this.em.createQuery(baseQuery,AddressEntity.class).getResultList());
	}

	@Override
	public ResponseEntity<?> delete(long addressId) {
		String sql = "DELETE FROM address WHERE address_id='"+addressId+"';";
		try {
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.ok("Address ["+addressId+"] was successfully deleted.");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Address ["+addressId+"] was not deleted.\n"+
					e.getSQLState()+"\n"+e.getLocalizedMessage());
		}
	}

	@Override
	public ResponseEntity<?> update(UpdateAddressRequest request) {
		Long cityId;
		try{
			cityId = request.getCity().getCityId();
		} catch (NoResultException | NullPointerException e){
			if (request.getCity().getCountry() != null && StringUtils.isNotEmpty(request.getCity().getName())){
				cityId = cityDaoImpl.insert(request.getCity().getCountry(),request.getCity().getName());
			} else {
				throw e;
			}
		}

		String sql = "UPDATE address SET ";

		if (request.getAddress()!=null && !request.getAddress().isEmpty())
			sql += "address = '"+request.getAddress()+"', ";
		if (request.getAddress2()!=null && !request.getAddress2().isEmpty())
			sql += "address2 = '"+request.getAddress2()+"', ";
		if (request.getCity()!= null)
			sql += "city_id = '"+ cityId + "', ";
		if (request.getDistrict()!=null && !request.getDistrict().isEmpty())
			sql += "district = '"+request.getDistrict()+"', ";
		if (request.getPhone()!=null && !request.getPhone().isEmpty())
			sql += "phone = '"+request.getPhone()+"', ";
		if (request.getPostalCode()!=null && !request.getPostalCode().isEmpty())
			sql += "postal_code = '"+request.getPostalCode()+"', ";

		sql = sql.substring(0,sql.length()-2) + " WHERE address_id = '"+request.getAddressId()+"';";

		try {
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.ok("Address ["+request.getAddressId()+"] was successfully updated.");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Address ["+request.getAddressId()+"] was not updated.\n"+
					e.getSQLState()+"\n"+e.getLocalizedMessage());
		}
	}

	@Override
	public ResponseEntity<?> insert(CreateAddressRequest request) {
		Long cityId;
		try{
			cityId = request.getCity().getCityId();
		} catch (NoResultException | NullPointerException e){
			if (request.getCity().getCountry() != null && StringUtils.isNotEmpty(request.getCity().getName())){
				cityId = cityDaoImpl.insert(request.getCity().getCountry(),request.getCity().getName());
			} else {
				throw e;
			}
		}

		String sql = "INSERT INTO address (address, address2, district, city_id, postal_code, phone) VALUES " +
				"('"+request.getAddress()+"', '"+
				request.getAddress2()+"', '"+
				request.getDistrict()+"', '"+
				cityId+"', '"+
				request.getPostalCode()+"', '"+
				request.getPhone()+"');";
		try {
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.ok(getNewestAddress());
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Address was not created!\n"+e.getSQLState()+"\n"+e.getLocalizedMessage());
		}
	}

	private Address getNewestAddress() throws SQLException {
		String sql = "SELECT address_id, address, address2, district, city_id, postal_code, phone FROM address ORDER BY last_update DESC LIMIT 1";

		ResultSet resultSet = connection.createStatement().executeQuery(sql);
		resultSet.next();

		Address address = new Address();

		address.setAddressId(resultSet.getLong("address_id"));
		address.setAddress(resultSet.getString("address"));
		address.setAddress2(resultSet.getString("address2"));
		address.setCity(cityDaoImpl.getById(resultSet.getLong("city_id")));
		address.setDistrict(resultSet.getString("district"));
		address.setPhone(resultSet.getString("phone"));
		address.setPostalCode(resultSet.getString("postal_code"));

		return address;
	}

	private List<Address> convertEntitiesToGenerate(List<AddressEntity> resultList) {
		List<Address> addresses = new ArrayList<>();

		for (AddressEntity addressEntity : resultList) {
			addresses.add(convertEntityToGenerated(addressEntity));
		}

		return addresses;
	}

	private Address convertEntityToGenerated(AddressEntity entity) {
		Address address = new Address();

		//System.out.println("address = ["+entity.getAddress()+"]");
		address.setAddress(entity.getAddress());

		//System.out.println("address2 = ["+entity.getAddress2()+"]");
		address.setAddress2(entity.getAddress2());

		//System.out.println("address id = ["+entity.getAddress_id()+"]");
		address.setAddressId(entity.getAddress_id());

		//System.out.println("city id = ["+entity.getCity_id()+"]");
		address.setCity(cityDaoImpl.getById(entity.getCity_id()));

		//System.out.println("district = ["+entity.getDistrict()+"]");
		address.setDistrict(entity.getDistrict());

		//System.out.println("postal code = ["+entity.getPostal_code()+"]");
		address.setPostalCode(entity.getPostal_code());

		//System.out.println("phone = ["+entity.getPhone()+"]");
		address.setPhone(entity.getPhone());

		return address;
	}
}
