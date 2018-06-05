package training.database.dao;

import java.math.BigInteger;

public interface FilmCategoryDao {
    String getById(long film_id);
	boolean insert(BigInteger filmId, String category);
}
