package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import soap.database.Database;
import soap.database.entity.AddressEntity;
import soap.generated.Address;
import soap.generated.CreateAddressRequest;
import soap.generated.UpdateAddressRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class AddressDao extends Database {
	@PersistenceContext @Lazy
	private EntityManager em;
	@Autowired @Lazy private CityDao cityDao;

//	@Autowired
//	public void setEm(@Lazy EntityManager em) {
//		this.em = em;
//	}
//
//	@Autowired
//	public void setCityDao(@Lazy CityDao cityDao) {
//		this.cityDao = cityDao;
//	}

	public Address getById(long id){
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<AddressEntity> query = criteriaBuilder.createQuery(AddressEntity.class);
		Root<AddressEntity> root = query.from(AddressEntity.class);
		query.where(criteriaBuilder.equal(root.get("address_id"),id));
		return convertEntityToGenerated(this.em.createQuery(query).getSingleResult());
	}

	public List<Address> getAll() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<AddressEntity> query = criteriaBuilder.createQuery(AddressEntity.class);
		return convertEntitiesToGenerate(this.em.createQuery(query).getResultList());
	}

	public void delete(long addressId) {
		String sql = "DELETE FROM address WHERE address_id='"+addressId+"';";
		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(UpdateAddressRequest request) {
		String sql = "UPDATE address SET ";

		if (request.getAddress()!=null)
			sql += "address = '"+request.getAddress()+"', ";
		if (request.getAddress2()!=null)
			sql += "address2 = '"+request.getAddress2()+"', ";
		if (request.getCity()!=null)
			sql += "city = '"+cityDao.getIdByName(request.getCity())+"', ";
		if (request.getDistrict()!=null)
			sql += "district = '"+request.getDistrict()+"', ";
		if (request.getPhone()!=null)
			sql += "phone = '"+request.getPhone()+"', ";
		if (request.getPostalCode()!=null)
			sql += "postal_code = '"+request.getPostalCode()+"', ";

		sql = sql.substring(0,sql.length()-3) + " WHERE address_id = '"+request.getAddressId()+"';";

		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insert(CreateAddressRequest request) {
		String sql = "INSERT INTO address (address, address2, district, city_id, postal_code, phone) VALUES " +
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
}
