package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.generated.Actor;
import training.generated.Film;
import training.persistence.dao.ActorDao;
import training.persistence.dto.ActorDto;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ActorService {
    @Autowired
    private ActorDao actorDao;

    public List<Actor> getAllActors() {
        return convert(actorDao.findAll());
    }

    private List<Actor> convert(Iterable<training.persistence.entity.Actor> entities){
        List<Actor> actors = new ArrayList<>();
        entities.forEach(a -> actors.add(a.makeGenerated()));
        return actors;
    }

    public Actor getActorById(int actorId) {
        return actorDao.findById(actorId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(actorId))).makeGenerated();
    }

    public List<Actor> getActorsByFirstName(String firstName) {
        return convert(actorDao.findByFirstName(firstName));
    }

    public List<Actor> getActorsByLastName(String lastName) {
        return convert(actorDao.findByLastName(lastName));
    }

    public List<Film> getFilmsWithActor(int actorId) {
        List<Film> films = new ArrayList<>();
        actorDao.findFilmsWithActor(actorId).forEach(f -> films.add(f.makeGenerated()));
        return films;
    }

    public Actor save(ActorDto actor) {
        training.persistence.entity.Actor entity;
        if (Objects.nonNull(actor.getId())){
            entity = actor.makeEntity(actorDao.findById(actor.getId()).orElseThrow(() -> new EntityNotFoundException(actor.getId().toString())));
        } else {
            entity = actor.makeEntity();
        }
        return actorDao.save(entity).makeGenerated();
    }

    public void delete(int actorId) {
        actorDao.deleteById(actorId);
    }

    public boolean exists(int actorId) {
        return actorDao.existsById(actorId);
    }
}
