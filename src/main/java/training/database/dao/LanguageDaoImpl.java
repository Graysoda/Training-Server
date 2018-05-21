package training.database.dao;

import org.springframework.stereotype.Repository;
import training.database.entity.LanguageEntity;
import training.generated.Language;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LanguageDaoImpl implements LanguageDao{
	protected EntityManager em;
	private static final String baseQuery = "SELECT l FROM sakila.language l";

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public String getLanguage(long id){
		if (id == -1)
			return "";

		return this.em.createQuery(baseQuery+" WHERE l.language_id = '"+id+"'",LanguageEntity.class).getSingleResult().getName();
	}

	@Override
	public long getId(String language){
		return this.em.createQuery(baseQuery+" WHERE l.name = '"+language+"'", LanguageEntity.class).getSingleResult().getLanguage_id();
	}

	@Override
	public List<Language> getAll() {
		return convertEntitysToGenerated(this.em.createQuery(baseQuery,LanguageEntity.class).getResultList());
	}

	private List<Language> convertEntitysToGenerated(List<LanguageEntity> resultList) {
		List<Language> languages = new ArrayList<>();

		for (LanguageEntity languageEntity : resultList) {
			languages.add(convertEntityToGenerated(languageEntity));
		}

		return languages;
	}

	private Language convertEntityToGenerated(LanguageEntity languageEntity) {
		Language language = new Language();

		language.setLanguageId(languageEntity.getLanguage_id());
		language.setName(languageEntity.getName());

		return language;
	}
}
