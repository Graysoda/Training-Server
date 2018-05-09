package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import soap.database.entity.FilmActorEntity;
import soap.generated.Actor;
import soap.generated.Summary;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class FilmActorDao {
    @PersistenceContext
    private EntityManager em;
    @Autowired @Lazy private FilmDao filmDao;
    @Autowired @Lazy private ActorDao actorDao;

    public List<Actor> getActorsFromFilm(long filmId){
        CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
        CriteriaQuery<FilmActorEntity> query = criteriaBuilder.createQuery(FilmActorEntity.class);
        Root<FilmActorEntity> from = query.from(FilmActorEntity.class);

        query.multiselect(makeSelections(from));

        query.where(criteriaBuilder.equal(from.get("film_id"),filmId));

        return actorDao.getActorsById(getActorIds(this.em.createQuery(query).getResultList()));
    }

    public List<Summary> getFilmsWithActor(long actorId){
        CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
        CriteriaQuery<FilmActorEntity> query = criteriaBuilder.createQuery(FilmActorEntity.class);
        Root<FilmActorEntity> from = query.from(FilmActorEntity.class);

        query.multiselect(makeSelections(from));

        query.where(criteriaBuilder.equal(from.get("actor_id"),actorId));

        return filmDao.getFilmsById(getFilmIds(this.em.createQuery(query).getResultList()));
    }

    private List<Long> getFilmIds(List<FilmActorEntity> resultList) {
        List<Long> filmIds = new ArrayList<>();

        for (FilmActorEntity entity : resultList) {
            filmIds.add(entity.getFilm_id());
        }

        return filmIds;
    }

    private List<Long> getActorIds(List<FilmActorEntity> resultList) {
        List<Long> actorIds = new ArrayList<>();

        for (FilmActorEntity entity : resultList) {
            actorIds.add(entity.getActor_id());
        }

        return actorIds;
    }

    private List<Selection<?>> makeSelections(Root<FilmActorEntity> from) {
        List<Selection<?>> selections = new ArrayList<>();

        selections.add(from.get("film_id"));
        selections.add(from.get("actor_id"));

        return selections;
    }
}
