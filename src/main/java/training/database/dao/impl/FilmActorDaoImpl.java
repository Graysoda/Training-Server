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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmActorDaoImpl implements FilmActorDao {
    protected EntityManager em;
    private Connection connection;
    private FilmDaoImpl filmDaoImpl;
    private ActorDaoImpl actorDaoImpl;
    private static final String baseQuery = "SELECT fa FROM sakila.film_actor fa";

    @Autowired @Lazy
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

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

    @Override
    public void insert(Long filmId, Long actorId) {
        if (filmDaoImpl.exists(filmId) && actorDaoImpl.exists(actorId)){
            String sql = "INSERT INTO film_actor (film_id, actor_id) VALUES ('"+filmId+"', '"+actorId+"');";

            try{
                connection.createStatement().executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
