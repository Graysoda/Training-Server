package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import soap.database.entity.StoreEntity;
import soap.generated.Store;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class StoreDao {
	@PersistenceContext @Lazy
	private EntityManager em;
	@Autowired @Lazy private AddressDao addressDao;
	@Autowired @Lazy private StaffDao staffDao;
	private static final String baseQuery = "SELECT s FROM sakila.store s";

	public Store getById(long id){
		try{
			return convertEntityToGenerated(this.em.createQuery(baseQuery+" WHERE s.store_id = '"+id+"'", StoreEntity.class).getSingleResult());
		} catch (javax.persistence.NoResultException e){
			//e.printStackTrace();
			return new Store();
		}
	}

	private Store convertEntityToGenerated(StoreEntity entity){
		Store store = new Store();

		//System.out.println("store id = ["+entity.getStore_id()+"]");
		store.setStoreId(entity.getStore_id());

		//System.out.println("address id = ["+entity.getAddress_id()+"]");
		store.setAddress(addressDao.getById(entity.getAddress_id()));

		//System.out.println("staff id = ["+entity.getStore_id()+"]");
		store.setManager(staffDao.getById(entity.getManager_staff_id()));

		//System.out.println("last update = ["+entity.getLast_update()+"]");
		store.setLastUpdate(entity.getLast_update());

		return store;
	}
}
