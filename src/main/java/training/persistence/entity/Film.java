package training.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private int id;
    private String title;
    private String description;
    @Column(name = "release_year")
    private int releaseYear;
    @ManyToOne
    @JoinColumn(referencedColumnName = "language_id", name = "language_id")
    private Language language;
    @ManyToOne
    @JoinColumn(referencedColumnName = "language_id", name = "original_language_id")
    private Language originalLanguage;
    @Column(name = "rental_duration")
    private int rentalDuration;
    @Column(name = "rental_rate")
    private float rentalRate;
    private int length;
    @Column(name = "replacement_cost")
    private float replacementCost;
    @Column(name = "special_features")
    private String specialFeatures;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "film_actor",
            joinColumns = @JoinColumn(
                    name = "film_id",
                    referencedColumnName = "film_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "actor_id",
                    referencedColumnName = "actor_id"
            )
    )
    private List<Actor> actors;

    @ManyToOne
    @JoinTable(
            name = "film_category",
            joinColumns = @JoinColumn(
                    name = "film_id",
                    referencedColumnName = "film_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id",
                    referencedColumnName = "category_id"
            )
    )
    private Category category;
    private String rating;

    public Film() {
    }

    public training.generated.Film makeGenerated() {
        training.generated.Film film = new training.generated.Film();

        List<training.generated.Actor> act = new ArrayList<>(actors.size());
        actors.forEach(a -> act.add(a.makeGenerated()));
        film.setActors(act);

        film.setFilmId(BigInteger.valueOf(id));
        film.setCategory(category.getName());
        film.setDescription(description);
        film.setLanguage(language.getName());
        film.setOriginalLanguage(Objects.nonNull(originalLanguage) ? originalLanguage.getName() : null);
        film.setLength(length);
        film.setRating(rating.toString());
        film.setReleaseYear(releaseYear);
        film.setRentalDuration(rentalDuration);
        film.setRentalRate(rentalRate);
        film.setReplacementCost(replacementCost);
        film.setSpecialFeatures(specialFeatures);
        film.setTitle(title);

        return film;
    }
}
