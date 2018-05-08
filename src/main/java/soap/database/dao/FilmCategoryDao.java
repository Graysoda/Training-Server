package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.entity.FilmCategoryEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmCategoryDao {
	@PersistenceContext
	private EntityManager em;
	@Autowired private CategoryDao categoryDao;
	private String baseQuery = "SELECT fc FROM sakila.film_category ";

    public String getById(long film_id) {
        CriteriaQuery<FilmCategoryEntity> query = em.getCriteriaBuilder().createQuery(FilmCategoryEntity.class);
        Root<FilmCategoryEntity> filmCategoryEntityRoot = query.from(FilmCategoryEntity.class);

        List<Selection<?>> selections = new ArrayList<>();
        selections.add(filmCategoryEntityRoot.get("film_id"));
        selections.add(filmCategoryEntityRoot.get("category_id"));

        query.multiselect(selections);
        query.where(em.getCriteriaBuilder().equal(filmCategoryEntityRoot.get("film_id"),film_id));

        FilmCategoryEntity fce = this.em.createQuery(query).getSingleResult();
        return categoryDao.getNameById(fce.getCategory_id());
	}
}
