package training.database.dao;

import training.generated.Actor;
import training.generated.Summary;

import java.util.List;

public interface FilmActorDao {
    List<Actor> getActorsFromFilm(long filmId);
    List<Summary> getFilmsWithActor(long actorId);
	void insert(Long filmId, Long actorId);
	void deleteFilm(long filmId);
	void deleteActor(long actorId);
}
