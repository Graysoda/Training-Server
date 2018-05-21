package training.service;

import training.generated.Language;

import java.util.List;

public interface LanguageService {
	List<Language> getAllLanguages();
	Language getLanguageById(long languageId);
	Language getLanguageByName(String name);
}
