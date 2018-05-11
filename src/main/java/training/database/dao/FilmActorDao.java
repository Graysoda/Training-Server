package training.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import training.database.entity.FilmActorEntity;
import training.generated.Actor;
import training.generated.Summary;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class FilmActorDao {
    @PersistenceContext
    private EntityManager em;
    @Autowired @Lazy private FilmDao filmDao;
    @Autowired @Lazy private ActorDao actorDao;
    private static final String baseQuery = "SELECT fa FROM sakila.film_actor fa";

    public List<Actor> getActorsFromFilm(long filmId){
        return actorDao.getActorsById(getActorIds(this.em.createQuery(baseQuery+" WHERE fa.film_id = '"+filmId+"'",FilmActorEntity.class).getResultList()));
    }

    public List<Summary> getFilmsWithActor(long actorId){
        return filmDao.getFilmsById(getFilmIds(this.em.createQuery(baseQuery+" WHERE fa.actor_id = '"+actorId+"'",FilmActorEntity.class).getResultList()));
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
}
