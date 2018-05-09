package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import soap.database.entity.LanguageEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@Repository
@Transactional
public class LanguageDao {
	@PersistenceContext
	private EntityManager em;

	@Autowired
	public void setEm(@Lazy EntityManager em) {
		this.em = em;
	}

	String getLanguage(long id){
		if (id == -1)
			return "";

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<LanguageEntity> query = criteriaBuilder.createQuery(LanguageEntity.class);
		Root<LanguageEntity> root = query.from(LanguageEntity.class);
		query.where(criteriaBuilder.equal(root.get("language_id"),id));

		return this.em.createQuery(query).getSingleResult().getName();
	}

	long getId(String language){
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<LanguageEntity> query = criteriaBuilder.createQuery(LanguageEntity.class);
		Root<LanguageEntity> root = query.from(LanguageEntity.class);
		query.where(criteriaBuilder.equal(root.get("name"),language));

		return this.em.createQuery(query).getSingleResult().getLanguage_id();
	}
}
