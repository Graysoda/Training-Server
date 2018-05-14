package training.database.dao;

public interface LanguageDao {
    String getLanguage(long id);
    long getId(String language);
}
