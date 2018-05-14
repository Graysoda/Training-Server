package training.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import training.database.entity.AddressEntity;
import training.generated.Address;
import training.generated.CreateAddressRequest;
import training.generated.UpdateAddressRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AddressDaoImpl implements AddressDao{
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
	public List<Address> getAll() {
		return convertEntitiesToGenerate(this.em.createQuery(baseQuery,AddressEntity.class).getResultList());
	}

	@Override
	public void delete(long addressId) {
		String sql = "DELETE FROM address WHERE address_id='"+addressId+"';";
		try {
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(UpdateAddressRequest request) {
		String sql = "UPDATE address SET ";

		if (request.getAddress()!=null)
			sql += "address = '"+request.getAddress()+"', ";
		if (request.getAddress2()!=null)
			sql += "address2 = '"+request.getAddress2()+"', ";
		if (request.getCity()!=null)
			sql += "city = '"+ cityDaoImpl.getIdByName(request.getCity())+"', ";
		if (request.getDistrict()!=null)
			sql += "district = '"+request.getDistrict()+"', ";
		if (request.getPhone()!=null)
			sql += "phone = '"+request.getPhone()+"', ";
		if (request.getPostalCode()!=null)
			sql += "postal_code = '"+request.getPostalCode()+"', ";

		sql = sql.substring(0,sql.length()-2) + " WHERE address_id = '"+request.getAddressId()+"';";

		try {
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(CreateAddressRequest request) {
		String sql = "INSERT INTO address (address, address2, district, city_id, postal_code, phone) VALUES " +
				"('"+request.getAddress()+"', '"+
				request.getAddress2()+"', '"+
				request.getDistrict()+"', '"+
				cityDaoImpl.getIdByName(request.getCity())+"', '"+
				request.getPostalCode()+"', '"+
				request.getPhone()+"');";
		try {
			connection.createStatement().executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
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
		address.setCity(cityDaoImpl.getNameById(entity.getCity_id()));

		//System.out.println("district = ["+entity.getDistrict()+"]");
		address.setDistrict(entity.getDistrict());

		//System.out.println("postal code = ["+entity.getPostal_code()+"]");
		address.setPostalCode(entity.getPostal_code());

		//System.out.println("phone = ["+entity.getPhone()+"]");
		address.setPhone(entity.getPhone());

		//System.out.println("last update = ["+entity.getLast_update()+"]");
		address.setLastUpdate(entity.getLast_update());

		return address;
	}
}