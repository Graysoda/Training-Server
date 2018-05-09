package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.Database;
import soap.database.entity.InventoryEntity;
import soap.generated.CreateInventoryRequest;
import soap.generated.Inventory;
import soap.generated.UpdateInventoryRequest;

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
public class InventoryDao extends Database {
	@PersistenceContext
	private EntityManager em;
	@Autowired private FilmDao filmDao;
	@Autowired private StoreDao storeDao;

	public List<Inventory> getAll() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<InventoryEntity> query = criteriaBuilder.createQuery(InventoryEntity.class);
		query.multiselect(makeSelection(query.from(InventoryEntity.class)));

		return convertEntitiesToGenerated(this.em.createQuery(query).setMaxResults(20).getResultList());
	}

	public Inventory getById(long id) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<InventoryEntity> query = criteriaBuilder.createQuery(InventoryEntity.class);
		Root<InventoryEntity> root = query.from(InventoryEntity.class);
		query.where(criteriaBuilder.equal(root.get("inventory_id"),id));
		query.multiselect(makeSelection(root));

		return convertEntityToGenerated(this.em.createQuery(query).getSingleResult());
	}

	public List<Inventory> getStoreInventory(long storeId) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<InventoryEntity> query = criteriaBuilder.createQuery(InventoryEntity.class);
		Root<InventoryEntity> root = query.from(InventoryEntity.class);
		query.where(criteriaBuilder.equal(root.get("store_id"),storeId));
		query.multiselect(makeSelection(root));

		return convertEntitiesToGenerated(this.em.createQuery(query).setMaxResults(50).getResultList());
	}

	public void insert(CreateInventoryRequest request) {
		String sql = "INSERT INTO inventory (film_id, store_id) VALUES ("+request.getFilmId()+", "+request.getStoreId()+");";
		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(long inventoryId) {
		String sql = "DELETE FROM inventory WHERE inventory_id='"+inventoryId+"';";
		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(UpdateInventoryRequest request) {
		String sql = "UPDATE inventory SET ";

		if (request.getFilmId()!=null)
			sql += "film_id = '"+request.getFilmId()+"', ";
		if (request.getStoreId() != null)
			sql += "store_id = '"+request.getStoreId()+"', ";

		sql += sql.subSequence(0,sql.length()-3) + " WHERE inventory_id = '"+request.getInventoryId()+"';";

		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Inventory convertEntityToGenerated(InventoryEntity entity) {
		Inventory inventory = new Inventory();

		//System.out.println("inv id = ["+entity.getInventory_id()+"]");
		inventory.setInventoryId(entity.getInventory_id());
		//System.out.println("film id = ["+entity.getFilm_id()+"]");
		inventory.setFilm(filmDao.getById(entity.getFilm_id()));
		//System.out.println("store id = ["+entity.getStore_id()+"]");
		inventory.setStore(storeDao.getById(entity.getStore_id()));
		//System.out.println("last update = ["+entity.getLast_update()+"]");
		inventory.setLastUpdate(entity.getLast_update());

		return inventory;
	}

	private List<Inventory> convertEntitiesToGenerated(List<InventoryEntity> entities) {
		List<Inventory> inventories = new ArrayList<>();

		for (InventoryEntity entity : entities) {
			inventories.add(convertEntityToGenerated(entity));
		}

		return inventories;
	}

	private List<Selection<?>> makeSelection(Root<InventoryEntity> from) {
		List<Selection<?>> selections = new ArrayList<>();
		selections.add(from.get("inventory_id"));
		selections.add(from.get("store_id"));
		selections.add(from.get("film_id"));
		selections.add(from.get("last_update"));

		return selections;
	}
}
