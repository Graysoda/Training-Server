package soap.database.dao;

import org.springframework.stereotype.Repository;
import soap.database.entity.SummaryEntity;
import soap.generated.Summary;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

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

		summary.setFilmId(entity.getFilm_id());
		summary.setTitle(entity.getTitle());
		summary.setDescription(entity.getDescription());

		return summary;
	}

	public List<Summary> getByIds(long[] ids) {
		StringBuilder query = new StringBuilder(baseQuery+"WHERE IN(");

		for (long id : ids) {
			if (!query.toString().contains(String.valueOf(id)))
				query.append("'").append(id).append("', ");
		}

		query.delete(query.lastIndexOf(","),query.indexOf(" ")).append(");");

		return convertEntitiesToGenerated(this.em.createQuery(query.toString(),SummaryEntity.class).getResultList());
	}

	private List<Summary> convertEntitiesToGenerated(List<SummaryEntity> resultList) {
		List<Summary> summaries = new ArrayList<>();

		for (SummaryEntity summaryEntity : resultList) {
			summaries.add(convertEntityToGenerated(summaryEntity));
		}

		return summaries;
	}
}
