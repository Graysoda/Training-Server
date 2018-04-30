package soap.database.entity;

import javax.persistence.*;

@Entity(name = "sakila.language")
@Table(name = "language")
public class LanguageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long language_id;
	private String name;
	private String last_update;

	public long getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(long language_id) {
		this.language_id = language_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLast_update() {
		return last_update;
	}

	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}
}
