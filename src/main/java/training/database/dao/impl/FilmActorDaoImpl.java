package training.database.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import training.database.dao.FilmActorDao;
import training.database.entity.FilmActorEntity;
import training.generated.Actor;
import training.generated.Summary;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmActorDaoImpl implements FilmActorDao {
    protected EntityManager em;
    private FilmDaoImpl filmDaoImpl;
    private ActorDaoImpl actorDaoImpl;
    private static final String baseQuery = "SELECT fa FROM sakila.film_actor fa";

    public EntityManager getEm() {
        return em;
    }

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Autowired @Lazy
    public void setActorDaoImpl(ActorDaoImpl actorDaoImpl) {
        this.actorDaoImpl = actorDaoImpl;
    }

    @Autowired @Lazy
    public void setFilmDaoImpl(FilmDaoImpl filmDaoImpl) {
        this.filmDaoImpl = filmDaoImpl;
    }

    public List<Actor> getActorsFromFilm(long filmId){
        return actorDaoImpl.getActorsById(getActorIds(this.em.createQuery(baseQuery+" WHERE fa.film_id = '"+filmId+"'",FilmActorEntity.class).getResultList()));
    }

    public List<Summary> getFilmsWithActor(long actorId){
        return filmDaoImpl.getFilmsById(getFilmIds(this.em.createQuery(baseQuery+" WHERE fa.actor_id = '"+actorId+"'",FilmActorEntity.class).getResultList()));
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
