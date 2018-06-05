package training.database.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import training.database.dao.LanguageDao;
import training.database.entity.LanguageEntity;
import training.generated.Language;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LanguageDaoImpl implements LanguageDao {
	protected EntityManager em;
	private Connection connection;
	private static final String baseQuery = "SELECT l FROM sakila.language l";

	@Autowired @Lazy
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public String getLanguage(long id){
		if (id == -1)
			return "";

		return this.em.createQuery(baseQuery+" WHERE l.language_id = '"+id+"'",LanguageEntity.class).getSingleResult().getName();
	}

	@Override
	public long getId(String language){
		try {
			return this.em.createQuery(baseQuery+" WHERE l.name = '"+language+"'", LanguageEntity.class).getSingleResult().getLanguage_id();
		} catch (NoResultException e){
			return insert(language);
		}
	}

	private long insert(String language) {
		String sql = "INSERT INTO (name) VALUES ('"+language+"')";

		try{
			connection.createStatement().executeUpdate(sql);
			return getNewestLanguage();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	private long getNewestLanguage() throws SQLException {
		String sql = "SELECT language_id FROM language ORDER BY last_update DESC LIMIT 1";

		ResultSet resultSet = connection.createStatement().executeQuery(sql);
		resultSet.next();

		return resultSet.getLong("language_id");
	}

	@Override
	public List<Language> getAll() {
		return convertEntitysToGenerated(this.em.createQuery(baseQuery,LanguageEntity.class).getResultList());
	}

	private List<Language> convertEntitysToGenerated(List<LanguageEntity> resultList) {
		List<Language> languages = new ArrayList<>();

		for (LanguageEntity languageEntity : resultList) {
			languages.add(convertEntityToGenerated(languageEntity));
		}

		return languages;
	}

	private Language convertEntityToGenerated(LanguageEntity languageEntity) {
		Language language = new Language();

		language.setLanguageId(languageEntity.getLanguage_id());
		language.setName(languageEntity.getName());

		return language;
	}
}
