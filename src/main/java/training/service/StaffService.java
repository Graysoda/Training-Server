package training.service;

import training.generated.CreateStaffRequest;
import training.generated.Staff;
import training.generated.UpdateStaffRequest;

import java.util.List;

public interface StaffService {
	List<Staff> getAllStaff();
	Staff getStaffById(int id);
	void insertStaff(CreateStaffRequest request);
	void deleteStaff(long staffId);
	void updateStaff(UpdateStaffRequest request);
}
