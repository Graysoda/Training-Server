package training.database.dao;

public interface CityDao {
    String getNameById(long id);
    long getIdByName(String city);
}
