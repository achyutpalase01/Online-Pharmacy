package com.qsp.OnlinePharmacy.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qsp.OnlinePharmacy.entity.Staff;
import com.qsp.OnlinePharmacy.repository.StaffRepo;

@Repository
public class StaffDao {

	@Autowired
	private StaffRepo staffRepo;
	
	public Staff saveStaff(Staff staff) {
		return staffRepo.save(staff);
	}

	public Staff updateStaff(int staffId, Staff staff) {
	Optional<Staff> optional=staffRepo.findById(staffId);
	if(optional.isPresent()) {
		Staff oldStaff=optional.get();
		staff.setStaffId(staffId);
		staff.setAdmin(oldStaff.getAdmin());
		staff.setMedicalStore(oldStaff.getMedicalStore());
		return staffRepo.save(staff);
	}
		return null;
	}

	public Staff getStaffById(int staffId) {
	Optional<Staff> optional=staffRepo.findById(staffId);
	if(optional.isPresent()) {
		return optional.get();
	}
		return null;
	}

	public Staff deleteStaffById(int staffId) {
		Optional<Staff> optional=staffRepo.findById(staffId);
		if(optional.isPresent()) {
			staffRepo.deleteById(staffId);
			return optional.get();
		}
		return null;
	}
}
