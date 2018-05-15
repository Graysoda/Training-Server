package training.database.dao;

import org.springframework.http.ResponseEntity;
import training.generated.CreateStaffRequest;
import training.generated.Staff;
import training.generated.UpdateStaffRequest;

import java.util.List;

public interface StaffDao {
    List<Staff> getAll();
    Staff getById(long id);
    ResponseEntity<?> insert(CreateStaffRequest request);
    ResponseEntity<?> update(UpdateStaffRequest request);
    ResponseEntity<?> delete(long id);
}
