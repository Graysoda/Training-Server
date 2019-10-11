package training.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "language")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private int id;
    private String name;

    public Language() {
    }

    public training.generated.Language makeGenerated() {
        training.generated.Language language = new training.generated.Language();
        language.setLanguageId(id);
        language.setName(name);
        return language;
    }
}
