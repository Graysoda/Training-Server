//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package soap.database.dao;

import org.springframework.stereotype.Repository;
import soap.database.entity.ActorEntity;
import soap.generated.Actor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ActorDao {
	@PersistenceContext
	private EntityManager em;
	private String baseQuery = "SELECT a FROM sakila.actor a ";

	public ActorDao() {
	}

	public List<Actor> getActors() {
		ArrayList<Actor> actors = new ArrayList<>();
		TypedQuery<ActorEntity> query = this.em.createQuery(this.baseQuery, ActorEntity.class);

		for (Object actor : query.getResultList()) {
			actors.add(this.convertActorEntityToGenerated((ActorEntity) actor));
		}

		return actors;
	}

	public Actor findById(long id) {
		System.out.println("Actor find by id [" + id + "]");
		ActorEntity actor = (ActorEntity)this.em.createQuery(this.baseQuery + "WHERE a.actor_id = " + id + "").getSingleResult();
		return this.convertActorEntityToGenerated(actor);
	}

	private Actor convertActorEntityToGenerated(ActorEntity actorEntity) {
		Actor actor = new Actor();
		actor.setActorId((int)actorEntity.getActor_id());
		actor.setFirstName(actorEntity.getFirst_name());
		actor.setLastName(actorEntity.getLast_name());
		actor.setLastUpdate(actorEntity.getLast_update());
		return actor;
	}
}
