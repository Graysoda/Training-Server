package training.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.generated.CreateStaffRequest;
import training.generated.Staff;
import training.generated.UpdateStaffRequest;

import java.util.List;

@Service
public interface StaffService {
	@Transactional
	List<Staff> getAllStaff();
	@Transactional
	Staff getStaffById(int id);
	@Transactional
	ResponseEntity<?> insertStaff(CreateStaffRequest request);
	@Transactional
	ResponseEntity<?> deleteStaff(long staffId);
	@Transactional
	ResponseEntity<?> updateStaff(UpdateStaffRequest request);
}
