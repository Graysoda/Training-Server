package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import soap.database.entity.FilmCategoryEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class FilmCategoryDao {
	@PersistenceContext @Lazy
	@Autowired private EntityManager em;
	@Autowired @Lazy private CategoryDao categoryDao;
	private static final String baseQuery = "SELECT fc FROM sakila.film_category fc";

//    @Autowired
//    public void setEm(@Lazy EntityManager em) {
//        this.em = em;
//    }
//
//	@Autowired
//    public void setCategoryDao(@Lazy CategoryDao categoryDao) {
//        this.categoryDao = categoryDao;
//    }

    public String getById(long film_id) {
//        CriteriaQuery<FilmCategoryEntity> query = em.getCriteriaBuilder().createQuery(FilmCategoryEntity.class);
//        Root<FilmCategoryEntity> filmCategoryEntityRoot = query.from(FilmCategoryEntity.class);
//
//        List<Selection<?>> selections = new ArrayList<>();
//        selections.add(filmCategoryEntityRoot.get("film_id"));
//        selections.add(filmCategoryEntityRoot.get("category_id"));
//
//        query.multiselect(selections);
//        query.where(em.getCriteriaBuilder().equal(filmCategoryEntityRoot.get("film_id"),film_id));
//
        FilmCategoryEntity fce = this.em.createQuery(baseQuery+" WHERE fc.film_id = '"+film_id+"'", FilmCategoryEntity.class).getSingleResult();

        return categoryDao.getNameById(fce.getCategory_id());
	}
}
