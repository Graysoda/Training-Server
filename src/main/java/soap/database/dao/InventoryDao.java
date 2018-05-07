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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InventoryDao extends Database {
	@PersistenceContext
	private EntityManager em;
	private FilmDao filmDao;
	private StoreDao storeDao;
	private String baseQuery = "SELECT i FROM sakila.inventory i ";

	@Autowired
	public void setFilmDao(FilmDao filmDao) {
		this.filmDao = filmDao;
	}

	@Autowired
	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public List<Inventory> getAll() throws SQLException {
		return convertEntitiesToGenerated(this.em.createQuery(baseQuery,InventoryEntity.class).getResultList());
	}

	private Inventory convertEntityToGenerated(InventoryEntity entity) throws SQLException {
		Inventory inventory = new Inventory();

		inventory.setInventoryId(entity.getInventory_id());
		inventory.setFilm(filmDao.getById(entity.getFilm_id()));
		inventory.setStore(storeDao.getById(entity.getStore_id()));
		inventory.setLastUpdate(entity.getLast_update());

		return inventory;
	}

	private List<Inventory> convertEntitiesToGenerated(List<InventoryEntity> entities) throws SQLException {
		List<Inventory> inventories = new ArrayList<>();

		for (InventoryEntity entity : entities) {
			inventories.add(convertEntityToGenerated(entity));
		}

		return inventories;
	}

	public Inventory getById(long id) throws SQLException {
		return convertEntityToGenerated(this.em.createQuery(baseQuery+"WHERE i.inventory_id='"+id+"'",InventoryEntity.class).getSingleResult());
	}

	public List<Inventory> getStoreInventory(long storeId) throws SQLException {
		return convertEntitiesToGenerated(this.em.createQuery(baseQuery+"WHERE i.store_id='"+storeId+"'",InventoryEntity.class).getResultList());
	}

	public void insert(CreateInventoryRequest request) {
		String sql = "INSERT INTO sakila.inventory (film_id, store_id) VALUES ("+request.getFilmId()+", "+request.getStoreId()+");";
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate( sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(long inventoryId) {
		String sql = "DELETE FROM sakila.inventory WHERE inventory_id='"+inventoryId+"';";
		try {
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(UpdateInventoryRequest request) {
		String sql = "UPDATE sakila.inventory SET ";

		if (request.getFilmId()!=null)
			sql += "film_id = '"+request.getFilmId()+"', ";
		if (request.getStoreId() != null)
			sql += "store_id = '"+request.getStoreId()+"', ";

		sql += sql.subSequence(0,sql.length()-3) + " WHERE inventory_id = '"+request.getInventoryId()+"';";

		try {
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
