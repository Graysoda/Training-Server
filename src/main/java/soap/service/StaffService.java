package soap.service;

import soap.generated.CreateStaffRequest;
import soap.generated.Staff;
import soap.generated.UpdateStaffRequest;

import java.util.List;

public interface StaffService {
	List<Staff> getAllStaff();
	Staff getStaffById(int id);
	void insertStaff(CreateStaffRequest request);
	void deleteStaff(long staffId);
	void updateStaff(UpdateStaffRequest request);
}
