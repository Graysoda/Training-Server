package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.entity.StoreEntity;
import soap.generated.Store;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class StoreDao {
	@PersistenceContext
	private EntityManager em;
	private AddressDao addressDao;
	private StaffDao staffDao;
	private String baseQuery = "SELECT s FROM sakila.store s ";

	@Autowired
	public void setAddressDao(AddressDao addressDao) {
		this.addressDao = addressDao;
	}

	@Autowired
	public void setStaffDao(StaffDao staffDao) {
		this.staffDao = staffDao;
	}

	public Store getById(long id){
		return convertEntityToGenerated(this.em
				.createQuery(baseQuery+"WHERE s.store_id='"+id+"'",StoreEntity.class)
				.getSingleResult());
	}

	private Store convertEntityToGenerated(StoreEntity entity){
		Store store = new Store();

		store.setStoreId(entity.getStore_id());
		store.setAddress(addressDao.getById(entity.getAddress_id()));
		store.setManager(staffDao.getById(entity.getManager_staff_id()));
		store.setLastUpdate(entity.getLast_update());

		return store;
	}
}
