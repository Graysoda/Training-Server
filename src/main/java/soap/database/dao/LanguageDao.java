package soap.database.dao;

import org.springframework.stereotype.Repository;
import soap.database.entity.LanguageEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class LanguageDao {
	@PersistenceContext
	private EntityManager em;
	private String baseQuery = "SELECT l FROM sakila.language l ";

	public LanguageDao() {}

	String getLanguage(long id){
		if (id == -1)
			return "";
		else
			return this.em.createQuery(baseQuery+"WHERE l.language_id = '"+id+"'",LanguageEntity.class).getSingleResult().getName();
	}

	long getId(String language){
		return this.em.createQuery(baseQuery+"WHERE l.name = '"+language+"'",LanguageEntity.class).getSingleResult().getLanguage_id();
	}
}
