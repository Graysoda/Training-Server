package training.database.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "sakila.actor")
@Table(name = "actor")
public class ActorEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long actor_id;
	@NotNull
	private String first_name;
	@NotNull
	private String last_name;

	public ActorEntity() {
	}

	public ActorEntity(long actor_id, @NotNull String first_name, @NotNull String last_name) {
		this.actor_id = actor_id;
		this.first_name = first_name;
		this.last_name = last_name;
	}

	public long getActor_id() {
		return this.actor_id;
	}

	public void setActor_id(long actor_id) {
		this.actor_id = actor_id;
	}

	public String getFirst_name() {
		return this.first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return this.last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
}
