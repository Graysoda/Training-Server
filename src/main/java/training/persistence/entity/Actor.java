package training.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "actor")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private int id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    public Actor() {
    }

    public Actor(training.generated.Actor actor) {
        if (Objects.nonNull(actor.getActorId())){
            id = new Long(actor.getActorId()).intValue();
        }
        if (Objects.nonNull(actor.getFirstName())){
            firstName = actor.getFirstName();
        }
        if (Objects.nonNull(actor.getLastName())){
            lastName = actor.getLastName();
        }
    }

    public training.generated.Actor makeGenerated() {
        training.generated.Actor actor = new training.generated.Actor();
        actor.setFirstName(firstName);
        actor.setLastName(lastName);
        actor.setActorId(id);
        return actor;
    }
}
