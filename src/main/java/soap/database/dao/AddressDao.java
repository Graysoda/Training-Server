package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.Database;
import soap.database.entity.AddressEntity;
import soap.generated.Address;
import soap.generated.CreateAddressRequest;
import soap.generated.UpdateAddressRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class AddressDao extends Database {
	@PersistenceContext private EntityManager em;
	@Autowired private CityDao cityDao;
	private String baseQuery = "SELECT adr FROM sakila.address adr ";

	public Address getById(long id){
		return convertEntityToGenerated(this.em.createQuery(baseQuery+"WHERE adr.address_id='"+id+"'",AddressEntity.class).getSingleResult());
	}

	public void insert(CreateAddressRequest request) {
		String sql = "INSERT INTO address (address.address, address.address2, address.district, address.city_id, address.postal_code, address.phone) VALUES " +
				"('"+request.getAddress()+"', '"+
				request.getAddress2()+"', '"+
				request.getDistrict()+"', '"+
				cityDao.getIdByName(request.getCity())+"', '"+
				request.getPostalCode()+"', '"+
				request.getPhone()+"');";
		try {
			getConnection().createStatement().executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Address> getAll() {
		return convertEntitiesToGenerate(this.em.createQuery(baseQuery,AddressEntity.class).getResultList());
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

		address.setAddress(entity.getAddress());
		address.setAddress2(entity.getAddress2());
		address.setAddressId(entity.getAddress_id());
		address.setCity(cityDao.getNameById(entity.getCity_id()));
		address.setDistrict(entity.getDistrict());
		address.setPostalCode(entity.getPostal_code());
		address.setPhone(entity.getPhone());
		address.setLastUpdate(entity.getLast_update());

		return address;
	}

	public void delete(long addressId) {
		String sql = "DELETE FROM address WHERE address.address_id='"+addressId+"';";
		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(UpdateAddressRequest request) {
		String sql = "UPDATE address SET ";

		if (request.getAddress()!=null)
			sql += "address.address = '"+request.getAddress()+"', ";
		if (request.getAddress2()!=null)
			sql += "address.address2 = '"+request.getAddress2()+"', ";
		if (request.getCity()!=null)
			sql += "address.city = '"+cityDao.getIdByName(request.getCity())+"', ";
		if (request.getDistrict()!=null)
			sql += "address.district = '"+request.getDistrict()+"', ";
		if (request.getPhone()!=null)
			sql += "address.phone = '"+request.getPhone()+"', ";
		if (request.getPostalCode()!=null)
			sql += "address.postal_code = '"+request.getPostalCode()+"', ";

		sql = sql.substring(0,sql.length()-3) + " WHERE address.address_id = '"+request.getAddressId()+"';";

		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
