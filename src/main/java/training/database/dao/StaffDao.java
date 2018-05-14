package training.database.dao;

import training.generated.CreateStaffRequest;
import training.generated.Staff;
import training.generated.UpdateStaffRequest;

import java.util.List;

public interface StaffDao {
    List<Staff> getAll();
    Staff getById(long id);
    void insert(CreateStaffRequest request);
    void update(UpdateStaffRequest request);
    void delete(long id);
}
