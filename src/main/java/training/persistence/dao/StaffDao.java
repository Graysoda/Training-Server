package training.persistence.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import training.persistence.entity.Staff;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffDao extends CrudRepository<Staff, Integer> {
    List<training.generated.Staff> findAllByStoreId(int storeId);

    @Query("SELECT s FROM Staff s WHERE s.active='1'")
    Iterable<Staff> findActive();

    @Query("SELECT s FROM Staff s WHERE s.active='0'")
    Iterable<Staff> findInactive();

    boolean existsByUsername(String username);

    Optional<Staff> findByUsername(String username);
}
