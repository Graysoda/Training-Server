package training.persistence.dto;

import lombok.Data;
import training.generated.CreateInventoryRequest;
import training.generated.UpdateInventoryRequest;
import training.persistence.entity.Film;
import training.persistence.entity.Inventory;
import training.persistence.entity.Store;

@Data
public class InventoryDto {
    private Integer id;
    private Integer film;
    private Integer store;

    public InventoryDto(){}

    public InventoryDto(CreateInventoryRequest request) {
        film = request.getFilmId();
        store = request.getStoreId();
    }

    public InventoryDto(UpdateInventoryRequest request) {
        id = request.getInventoryId();
        film = request.getFilmId();
        store = request.getStoreId();
    }

    public Inventory makeEntity(Film film, Store store){
        Inventory inventory = new Inventory();
        inventory.setId(id);
        inventory.setFilm(film);
        inventory.setStore(store);
        return inventory;
    }
}
