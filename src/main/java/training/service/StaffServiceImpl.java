package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.database.dao.StaffDao;
import training.generated.CreateStaffRequest;
import training.generated.Staff;
import training.generated.UpdateStaffRequest;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
	@Autowired @Lazy private StaffDao staffDao;

	@Override
	@Transactional
	public List<Staff> getAllStaff() {
		return staffDao.getAll();
	}

	@Override
	@Transactional
	public Staff getStaffById(int id) {
		return staffDao.getById(id);
	}

	@Override
	@Transactional
	public void insertStaff(CreateStaffRequest request) {
		staffDao.insert(request);
	}

	@Override
	@Transactional
	public void deleteStaff(long staffId) {
		staffDao.delete(staffId);
	}

	@Override
	@Transactional
	public void updateStaff(UpdateStaffRequest request) {
		staffDao.update(request);
	}
}
