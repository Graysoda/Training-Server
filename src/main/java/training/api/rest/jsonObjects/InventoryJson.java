package training.api.rest.jsonObjects;

public class InventoryJson {
    private Long filmId;
    private Long storeId;

    public InventoryJson(Long filmId, Long storeId) {
        this.filmId = filmId;
        this.storeId = storeId;
    }

    public InventoryJson() {
    }


    public Long getFilmId() {
        return filmId;
    }

    public Long getStoreId() {
        return storeId;
    }
}
