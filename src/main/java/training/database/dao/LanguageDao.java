package training.database.dao;

import training.generated.Language;

import java.util.List;

public interface LanguageDao {
    String getLanguage(long id);
    long getId(String language);
	List<Language> getAll();
}
