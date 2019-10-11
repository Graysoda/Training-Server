package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.generated.Inventory;
import training.persistence.dao.FilmDao;
import training.persistence.dao.InventoryDao;
import training.persistence.dao.StoreDao;
import training.persistence.dto.InventoryDto;
import training.persistence.entity.Film;
import training.persistence.entity.Store;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class InventoryService {
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private FilmDao filmDao;
    @Autowired
    private StoreDao storeDao;

    public List<Inventory> getAllInventory() {
        return convert(inventoryDao.findAll());
    }

    private List<Inventory> convert(Iterable<training.persistence.entity.Inventory> all) {
        List<Inventory> inventories = new ArrayList<>();
        all.forEach(i -> inventories.add(i.makeGenerate()));
        return inventories;
    }

    public Inventory getInevntoryById(int inventoryId) {
        return inventoryDao.findById(inventoryId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(inventoryId))).makeGenerate();
    }

    public List<Inventory> getStoreInventory(int storeId) {
        return convert(inventoryDao.findByStoreId(storeId));
    }

    public Inventory save(InventoryDto inventory) {
        training.persistence.entity.Inventory entity;

        if (Objects.nonNull(inventory.getId())){
            training.persistence.entity.Inventory i = inventoryDao.findById(inventory.getId())
                    .orElseThrow(() -> new EntityNotFoundException(inventory.getId().toString()));

            if (Objects.nonNull(inventory.getStore())){
                i.setStore(storeDao.findById(inventory.getStore())
                        .orElseThrow(() -> new EntityNotFoundException(inventory.getStore().toString())));
            }
            if (Objects.nonNull(inventory.getFilm())){
                i.setFilm(filmDao.findById(inventory.getFilm())
                        .orElseThrow(() -> new EntityNotFoundException(inventory.getFilm().toString())));
            }

            entity = inventory.makeEntity(i.getFilm(), i.getStore());
        } else {
            Film film = filmDao.findById(inventory.getFilm())
                    .orElseThrow(() -> new EntityNotFoundException(String.valueOf(inventory.getFilm())));
            Store store = storeDao.findById(inventory.getStore())
                    .orElseThrow(() -> new EntityNotFoundException(String.valueOf(inventory.getStore())));

            entity = inventory.makeEntity(film, store);
        }


        return inventoryDao.save(entity).makeGenerate();
    }

    public void delete(int inventoryId) {
        inventoryDao.deleteById(inventoryId);
    }

    public boolean exists(int inventoryId) {
        return inventoryDao.existsById(inventoryId);
    }
}
