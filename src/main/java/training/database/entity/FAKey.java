package training.database.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FAKey implements Serializable {
	@Column(name = "actor_id", nullable = false)
	private long actor_id;
	@Column(nullable = false, name = "film_id")
	private long film_id;
}
