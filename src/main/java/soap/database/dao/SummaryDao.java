package soap.database.dao;

import org.springframework.stereotype.Repository;
import soap.database.entity.SummaryEntity;
import soap.generated.Summary;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class SummaryDao {
	@PersistenceContext
	private EntityManager em;
	private String baseQuery = "SELECT s FROM sakila.film_text s ";

	public Summary getById(long id){
		return convertEntityToGenerated(this.em.createQuery(baseQuery+"WHERE s.film_id = '"+id+"'",SummaryEntity.class).getSingleResult());
	}

	private Summary convertEntityToGenerated(SummaryEntity entity){
		Summary summary = new Summary();

		summary.setTitle(entity.getTitle());
		summary.setDescription(entity.getDescription());

		return summary;
	}
}
