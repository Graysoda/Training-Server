package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.generated.Language;
import training.persistence.dao.LanguageDao;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LanguageService {
    @Autowired
    private LanguageDao languageDao;

    public boolean exists(int languageId){
        return languageDao.existsById(languageId);
    }

    public boolean exists(String language){
        return languageDao.existsByName(language);
    }

    public List<Language> getAllLanguages() {
        return convert(languageDao.findAll());
    }

    private List<Language> convert(Iterable<training.persistence.entity.Language> all) {
        List<Language> languages = new ArrayList<>();
        all.forEach(l -> languages.add(l.makeGenerated()));
        return languages;
    }

    public Language getLanguageById(int languageId) {
        return languageDao.findById(languageId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(languageId))).makeGenerated();
    }

    public Language getLanguageByName(String languageName) {
        return languageDao.findByName(languageName)
                .orElseThrow(() -> new EntityNotFoundException(languageName)).makeGenerated();
    }
}
