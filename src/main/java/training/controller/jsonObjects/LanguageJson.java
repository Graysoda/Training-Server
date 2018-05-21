package training.controller.jsonObjects;

public class LanguageJson {
	private String name;
	private Long languageId;

	public LanguageJson() {
	}

	public LanguageJson(String name, Long languageId) {
		this.name = name;
		this.languageId = languageId;
	}

	public String getName() {
		return name;
	}

	public Long getLanguageId() {
		return languageId;
	}
}
