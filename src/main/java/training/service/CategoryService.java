package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.persistence.dao.CategoryDao;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    public boolean exists(int categoryId){
        return categoryDao.existsById(categoryId);
    }

    public boolean exists(String category){
        return categoryDao.existsByName(category);
    }
}
