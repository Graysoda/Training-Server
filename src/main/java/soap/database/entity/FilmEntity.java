package soap.database.entity;

import org.springframework.lang.Nullable;
import soap.generated.CreateFilmRequest;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "sakila.film")
@Table(name = "film")
public class FilmEntity implements Film{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long filmId;
	@NotNull private String title;
	@NotNull private String description;
	@NotNull private int releaseYear;
	@NotNull private long languageId;
	@Nullable private long originalLanguage;
	@NotNull private int rentalDuration;
	@NotNull private float rentalRate;
	@NotNull private int length;
	@NotNull private float replacementCost;
	@NotNull private String rating;
	@NotNull private String specialFeatures;
	@NotNull private String lastUpdate;

	public FilmEntity() {
	}

	public FilmEntity(CreateFilmRequest request) {
		this.title = request.getTitle();
		this.description = request.getDescription();
		this.languageId = request.getLanguageId();
		this.releaseYear = request.getReleaseYear();
		this.rentalDuration = request.getRentalDuration();
		this.rentalRate = request.getRentalRate();
		this.length = request.getLength();
		this.replacementCost = request.getReplacementCost();
		this.rating = request.getRating();
		this.specialFeatures = request.getSpecialFeatures();
		this.originalLanguage = 0;
	}

	public long getFilmId() {
		return filmId;
	}

	public void setFilmId(long filmId) {
		this.filmId = filmId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(long languageId) {
		this.languageId = languageId;
	}

//	public long getOriginal_language() {
//		return originalLanguage;
//	}
//
//	public void setOriginal_language(long originalLanguage) {
//		this.originalLanguage = originalLanguage;
//	}

	public int getRentalDuration() {
		return rentalDuration;
	}

	public void setRentalDuration(int rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public float getRentalRate() {
		return rentalRate;
	}

	public void setRentalRate(float rentalRate) {
		this.rentalRate = rentalRate;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public float getReplacementCost() {
		return replacementCost;
	}

	public void setReplacementCost(float replacementCost) {
		this.replacementCost = replacementCost;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getSpecialFeatures() {
		return specialFeatures;
	}

	public void setSpecialFeatures(String specialFeatures) {
		this.specialFeatures = specialFeatures;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public long getOriginalLanguage() {
		return originalLanguage;
	}

	public void setOriginalLanguage(long originalLanguage) {
		this.originalLanguage = originalLanguage;
	}

	@Override
	public String toString() {
		return "filmId="+ filmId +", " +
				"title="+title+", " +
				"description="+description+", " +
				"languageId="+ languageId +", " +
				"releaseYear="+ releaseYear +", " +
				"rentalDuration="+ rentalDuration +", " +
				"rentalRate="+ rentalRate +", " +
				"length="+length+", " +
				"replacementCost="+ replacementCost +", " +
				"rating="+rating+", " +
				"specialFeatures="+ specialFeatures +", " +
				"lastUpdate="+ lastUpdate;
	}
}
