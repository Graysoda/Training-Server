package training.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "film_id",referencedColumnName = "film_id")
    private Film film;

    @ManyToOne
    @JoinColumn(name = "store_id",referencedColumnName = "store_id")
    private Store store;

    public Inventory() {
    }

    public training.generated.Inventory makeGenerate() {
        training.generated.Inventory inventory = new training.generated.Inventory();
        inventory.setInventoryId(id);
        inventory.setFilm(film.makeGenerated());
        inventory.setStore(store.makeGenerated());
        return inventory;
    }
}
