package soap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soap.database.dao.StaffDao;
import soap.generated.CreateStaffRequest;
import soap.generated.Staff;
import soap.generated.UpdateStaffRequest;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
	@Autowired @Lazy private StaffDao staffDao;

//	@Autowired
//	public void setStaffDao(@Lazy StaffDao staffDao) {
//		this.staffDao = staffDao;
//	}

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
