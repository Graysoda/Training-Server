package training.persistence.dto;

import lombok.Data;
import org.springframework.lang.Nullable;
import training.generated.CreateFilmRequest;
import training.generated.UpdateFilmRequest;
import training.persistence.entity.Actor;
import training.persistence.entity.Category;
import training.persistence.entity.Film;
import training.persistence.entity.Language;

import java.util.List;
import java.util.Objects;

@Data
public class FilmDto {
    Integer id;
    String title;
    String description;
    Integer releaseYear;
    String language;
    String originalLanguage;
    Integer rentalDuration;
    Float rentalRate;
    Integer length;
    Float replacementCost;
    String rating;
    String specialFeatures;
    List<Integer> actors;
    String category;

    public FilmDto(){}

    public FilmDto(CreateFilmRequest request) {
        actors.addAll(request.getActorId());
        category = request.getCategory();
        title = request.getTitle();
        description = request.getDescription();
        releaseYear = request.getReleaseYear();
        language = request.getLanguage();
        originalLanguage = request.getOriginalLanguage();
        rentalDuration = request.getRentalDuration();
        rentalRate = request.getRentalRate();
        length = request.getLength();
        replacementCost = request.getReplacementCost();
        rating = request.getRating();
        specialFeatures = request.getSpecialFeatures();
        category = request.getCategory();
    }

    public FilmDto(UpdateFilmRequest request) {
        id = request.getFilmId();
        actors.addAll(request.getActorId());
        category = request.getCategory();
        title = request.getTitle();
        description = request.getDescription();
        releaseYear = request.getReleaseYear();
        language = request.getLanguage();
        originalLanguage = request.getOriginalLanguage();
        rentalDuration = request.getRentalDuration();
        rentalRate = request.getRentalRate();
        length = request.getLength();
        replacementCost = request.getReplacementCost();
        rating = request.getRating();
        specialFeatures = request.getSpecialFeatures();
        category = request.getCategory();
    }

    public Film makeEntity(Language language, List<Actor> actors, Category category, @Nullable Language originalLanguage){
        Film film = new Film();
        film.setId(id);
        film.setDescription(description);
        film.setCategory(category);
        film.setActors(actors);
        film.setLanguage(language);
        film.setLength(length);
        film.setRating(rating);
        //film.setRating(MpaaRating.getInstance(rating));
        film.setReleaseYear(releaseYear);
        film.setRentalDuration(rentalDuration);
        film.setRentalRate(rentalRate);
        film.setReplacementCost(replacementCost);
        film.setSpecialFeatures(specialFeatures);
        film.setTitle(title);
        if (Objects.nonNull(originalLanguage)){
            film.setOriginalLanguage(originalLanguage);
        }
        return film;
    }

    public Film makeEntity(Film f) {
        if (Objects.isNull(description)){
            description = f.getDescription();
        }
        if (Objects.isNull(length)){
            length = f.getLength();
        }
        if (Objects.isNull(rating)){
            rating = f.getRating().toString();
        }
        if (Objects.isNull(releaseYear)){
            releaseYear = f.getReleaseYear();
        }
        if (Objects.isNull(rentalDuration)){
            rentalDuration = f.getRentalDuration();
        }
        if (Objects.isNull(rentalRate)){
            rentalRate = f.getRentalRate();
        }
        if (Objects.isNull(replacementCost)){
            replacementCost = f.getReplacementCost();
        }
        if (Objects.isNull(specialFeatures)){
            specialFeatures = f.getSpecialFeatures();
        }
        if (Objects.isNull(title)){
            title = f.getTitle();
        }
        return makeEntity(f.getLanguage(), f.getActors(), f.getCategory(), f.getOriginalLanguage());
    }
}
