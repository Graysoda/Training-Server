package training.database.dao;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import training.database.entity.LanguageEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class LanguageDao {
	@PersistenceContext @Lazy
	private EntityManager em;
	private static final String baseQuery = "SELECT l FROM sakila.language l";

	String getLanguage(long id){
		if (id == -1)
			return "";

		return this.em.createQuery(baseQuery+" WHERE l.language_id = '"+id+"'",LanguageEntity.class).getSingleResult().getName();
	}

	long getId(String language){
		return this.em.createQuery(baseQuery+" WHERE l.name = '"+language+"'", LanguageEntity.class).getSingleResult().getLanguage_id();
	}
}
