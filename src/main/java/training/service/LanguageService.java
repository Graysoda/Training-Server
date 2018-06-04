package training.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.generated.Language;

import java.util.List;

@Service
public interface LanguageService {
	@Transactional
	List<Language> getAllLanguages();
	@Transactional
	Language getLanguageById(long languageId);
	@Transactional
	Language getLanguageByName(String name);
}
