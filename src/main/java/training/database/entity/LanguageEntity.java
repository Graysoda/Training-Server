package training.database.entity;

import javax.persistence.*;

@Entity(name = "sakila.language")
@Table(name = "language")
public class LanguageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long language_id;
	private String name;

	public LanguageEntity() {
	}

	public LanguageEntity(long language_id, String name) {
		this.language_id = language_id;
		this.name = name;
	}

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
}
