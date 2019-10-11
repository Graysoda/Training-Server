package training.persistence.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import training.persistence.entity.Category;

@Repository
public interface CategoryDao extends CrudRepository<Category, Integer> {
    boolean existsByName(String category);

    Category findByName(String category);
}
