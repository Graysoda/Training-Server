package training.database.dao;

import org.springframework.stereotype.Repository;
import training.database.entity.LanguageEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

	public String getLanguage(long id){
		if (id == -1)
			return "";

		return this.em.createQuery(baseQuery+" WHERE l.language_id = '"+id+"'",LanguageEntity.class).getSingleResult().getName();
	}

	public long getId(String language){
		return this.em.createQuery(baseQuery+" WHERE l.name = '"+language+"'", LanguageEntity.class).getSingleResult().getLanguage_id();
	}
}
