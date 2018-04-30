package soap.database.entity;

public interface Film {
	public long getFilmId();
	public void setFilmId(long filmId);
	public String getTitle();
	public void setTitle(String title);
	public String getDescription();
	public void setDescription(String description);
	public int getReleaseYear();
	public void setReleaseYear(int releaseYear);
	public long getLanguageId();
	public void setLanguageId(long languageId);
	public int getRentalDuration();
	public void setRentalDuration(int rentalDuration);
	public float getRentalRate();
	public void setRentalRate(float rentalRate);
	public int getLength();
	public void setLength(int length);
	public float getReplacementCost();
	public void setReplacementCost(float replacementCost);
	public String getRating();
	public void setRating(String rating);
	public String getSpecialFeatures();
	public void setSpecialFeatures(String specialFeatures);
	public String getLastUpdate();
	public void setLastUpdate(String lastUpdate);
}
