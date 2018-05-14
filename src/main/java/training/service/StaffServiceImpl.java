package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.database.dao.StaffDaoImpl;
import training.generated.CreateStaffRequest;
import training.generated.Staff;
import training.generated.UpdateStaffRequest;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
	@Autowired @Lazy private StaffDaoImpl staffDaoImpl;

	@Override
	@Transactional
	public List<Staff> getAllStaff() {
		return staffDaoImpl.getAll();
	}

	@Override
	@Transactional
	public Staff getStaffById(int id) {
		return staffDaoImpl.getById(id);
	}

	@Override
	@Transactional
	public void insertStaff(CreateStaffRequest request) {
		staffDaoImpl.insert(request);
	}

	@Override
	@Transactional
	public void deleteStaff(long staffId) {
		staffDaoImpl.delete(staffId);
	}

	@Override
	@Transactional
	public void updateStaff(UpdateStaffRequest request) {
		staffDaoImpl.update(request);
	}
}
