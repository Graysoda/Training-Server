package training.service;

import org.springframework.http.ResponseEntity;
import training.generated.CreateStaffRequest;
import training.generated.Staff;
import training.generated.UpdateStaffRequest;

import java.util.List;

public interface StaffService {
	List<Staff> getAllStaff();
	Staff getStaffById(int id);
	ResponseEntity<?> insertStaff(CreateStaffRequest request);
	ResponseEntity<?> deleteStaff(long staffId);
	ResponseEntity<?> updateStaff(UpdateStaffRequest request);
}
