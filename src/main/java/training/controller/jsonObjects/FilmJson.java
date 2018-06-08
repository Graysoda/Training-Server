package training.controller.jsonObjects;

import java.util.List;

public class FilmJson {
    private String title;
    private String description;
    private String rating;
    private Integer length;
    private String language;
    private String originalLanguage;
    private Integer releaseYear;
    private Integer rentalDuration;
    private Float rentalRate;
    private Float replacementCost;
    private String specialFeatures;
    private String category;
    private List<Long> actors;

    public FilmJson(String title, String description, String rating, Integer length, String language,
                    String originalLanguage, Integer releaseYear, Integer rentalDuration, Float rentalRate,
                    Float replacementCost, String specialFeatures, String category, List<Long> actors) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.length = length;
        this.language = language;
        this.originalLanguage = originalLanguage;
        this.releaseYear = releaseYear;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.replacementCost = replacementCost;
        this.specialFeatures = specialFeatures;
        this.category = category;
        this.actors = actors;
    }

    public FilmJson() {
    }

    public String getCategory() {
        return category;
    }

    public List<Long> getActors() {
        return actors;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getRating() {
        return rating;
    }

    public Integer getLength() {
        return length;
    }

    public String getLanguage() {
        return language;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public Integer getRentalDuration() {
        return rentalDuration;
    }

    public Float getRentalRate() {
        return rentalRate;
    }

    public Float getReplacementCost() {
        return replacementCost;
    }

    public String getSpecialFeatures() {
        return specialFeatures;
    }
}
