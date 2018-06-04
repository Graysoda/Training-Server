package training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import training.database.dao.impl.StaffDaoImpl;
import training.generated.CreateStaffRequest;
import training.generated.Staff;
import training.generated.UpdateStaffRequest;
import training.service.StaffService;

import java.util.List;

public class StaffServiceImpl implements StaffService {
	@Autowired @Lazy private StaffDaoImpl staffDaoImpl;

	@Override
	public List<Staff> getAllStaff() {
		return staffDaoImpl.getAll();
	}

	@Override
	public Staff getStaffById(int id) {
		return staffDaoImpl.getById(id);
	}

	@Override
	public ResponseEntity<?> insertStaff(CreateStaffRequest request) {
		return staffDaoImpl.insert(request);
	}

	@Override
	public ResponseEntity<?> deleteStaff(long staffId) {
		return staffDaoImpl.delete(staffId);
	}

	@Override
	public ResponseEntity<?> updateStaff(UpdateStaffRequest request) {
		return staffDaoImpl.update(request);
	}
}
