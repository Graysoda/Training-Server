package training.persistence.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import training.persistence.entity.Language;

import java.util.Optional;

@Repository
public interface LanguageDao extends CrudRepository<Language, Integer> {
    boolean existsByName(String language);

    Optional<Language> findByName(String language);
}
