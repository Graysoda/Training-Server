package training.database.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "sakila.film")
@Table(name = "film")
public class FilmEntity{

	public enum mpaa_rating {
		G("G"),PG("PG"),PG13("PG-13"),R("R"),NC17("NC-17");

		private String rating;

		mpaa_rating(final String rating){
			this.rating = rating;
		}

		public String getRating() {
			return rating;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long film_id;
	@NotNull private String title;
	@NotNull private String description;
	@NotNull private int release_year;
	@NotNull private long language_id;
	@Nullable private Long original_language_id;
	@NotNull private int rental_duration;
	@NotNull private float rental_rate;
	@NotNull private int length;
	@NotNull private float replacement_cost;
	@NotNull private String rating;
	@NotNull private String special_features;
	@NotNull private String last_update;

	public FilmEntity(){}

	public FilmEntity(long film_id, @NotNull String title, @NotNull String description) {
		this.film_id = film_id;
		this.title = title;
		this.description = description;
	}

	public FilmEntity(long film_id, @NotNull String title, @NotNull String description, @NotNull int release_year, @NotNull long language_id, Long original_language_id, @NotNull int rental_duration, @NotNull float rental_rate, @NotNull int length, @NotNull float replacement_cost, @NotNull String rating, @NotNull String special_features, @NotNull String last_update) {
		this.film_id = film_id;
		this.title = title;
		this.description = description;
		this.release_year = release_year;
		this.language_id = language_id;
		this.original_language_id = original_language_id;
		this.rental_duration = rental_duration;
		this.rental_rate = rental_rate;
		this.length = length;
		this.replacement_cost = replacement_cost;
		this.rating = rating;
		this.special_features = special_features;
		this.last_update = last_update;

	}

	public long getFilm_id() {
		return film_id;
	}

	public void setFilm_id(long film_id) {
		this.film_id = film_id;
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

	public int getRelease_year() {
		return release_year;
	}

	public void setRelease_year(int release_year) {
		this.release_year = release_year;
	}

	public long getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(long language_id) {
		this.language_id = language_id;
	}

	public Long getOriginal_language_id() {
		return original_language_id;
	}

	public void setOriginal_language_id(@Nullable Long original_language_id) {
		this.original_language_id = original_language_id == null ? -1 : original_language_id;
	}

	public int getRental_duration() {
		return rental_duration;
	}

	public void setRental_duration(int rental_duration) {
		this.rental_duration = rental_duration;
	}

	public float getRental_rate() {
		return rental_rate;
	}

	public void setRental_rate(float rental_rate) {
		this.rental_rate = rental_rate;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public float getReplacement_cost() {
		return replacement_cost;
	}

	public void setReplacement_cost(float replacement_cost) {
		this.replacement_cost = replacement_cost;
	}

	public String getRating() {
		return rating;//.getRating();
	}

	public void setRating(@NotNull String rating) {
		this.rating = rating;
	}

	public String getSpecial_features() {
		return special_features;
	}

	public void setSpecial_features(String special_features) {
		this.special_features = special_features;
	}

	public String getLast_update() {
		return last_update;
	}

	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}

	@Override
	public String toString() {
		return "film_id="+ film_id +", " +
				"title="+title+", " +
				"description="+description+", " +
				"language_id="+ language_id +", " +
				"release_year="+ release_year +", " +
				"rental_duration="+ rental_duration +", " +
				"rental_rate="+ rental_rate +", " +
				"length="+length+", " +
				"replacement_cost="+ replacement_cost +", " +
				"rating="+rating+", " +
				"special_features="+ special_features +", " +
				"last_update="+ last_update;
	}
}
