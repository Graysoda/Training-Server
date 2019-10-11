package training.persistence.dto;

import lombok.Data;
import training.generated.CreateActorRequest;
import training.generated.UpdateActorRequest;
import training.persistence.entity.Actor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "actor", propOrder = {
        "actorId",
        "firstName",
        "lastName"
})
public class ActorDto {
    @XmlElement(name = "actor_id")
    private Integer id;
    private String firstName;
    private String lastName;

    public ActorDto(){}

    public ActorDto(CreateActorRequest request) {
        firstName = request.getFirstName();
        lastName = request.getLastName();
    }

    public ActorDto(UpdateActorRequest request) {
        id = request.getActorId();

        firstName = request.getNewFirstName();
        lastName = request.getNewLastName();
    }

    public Actor makeEntity() {
        Actor actor = new Actor();
        actor.setId(id);
        actor.setFirstName(firstName);
        actor.setLastName(lastName);
        return actor;
    }

    public Actor makeEntity(Actor entity) {
        if (Objects.isNull(firstName)){
            firstName = entity.getFirstName();
        }
        if (Objects.isNull(lastName)){
            lastName = entity.getLastName();
        }
        return makeEntity();
    }
}
