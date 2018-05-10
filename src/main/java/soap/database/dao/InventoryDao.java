package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import soap.database.Database;
import soap.database.entity.InventoryEntity;
import soap.generated.CreateInventoryRequest;
import soap.generated.Inventory;
import soap.generated.UpdateInventoryRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InventoryDao extends Database {
	@PersistenceContext @Lazy private EntityManager em;
	@Autowired @Lazy private FilmDao filmDao;
	@Autowired @Lazy private StoreDao storeDao;
	private static final String baseQuery = "SELECT inv FROM sakila.inventory inv";

	public List<Inventory> getAll() {
		return convertEntitiesToGenerated(this.em.createQuery(baseQuery,InventoryEntity.class).setMaxResults(100).getResultList());
	}

	public Inventory getById(long id) {
		return convertEntityToGenerated(this.em.createQuery(baseQuery+" WHERE inv.inventory_id = '"+id+"'", InventoryEntity.class).getSingleResult());
	}

	public List<Inventory> getStoreInventory(long storeId) {
		return convertEntitiesToGenerated(this.em.createQuery(baseQuery+" WHERE inv.store_id = '"+storeId+"'",InventoryEntity.class).getResultList());
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

		sql += sql.subSequence(0,sql.length()-2) + " WHERE inventory_id = '"+request.getInventoryId()+"';";

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
}
