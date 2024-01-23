package com.qsp.OnlinePharmacy.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qsp.OnlinePharmacy.entity.Admin;
import com.qsp.OnlinePharmacy.repository.AdminRepo;

@Repository
public class AdminDao {

	@Autowired
	private AdminRepo adminRepo;
	
	public Admin saveAdmin(Admin admin) {
		return adminRepo.save(admin);
	}

	public Admin getAdmin(int adminId) {
		Optional<Admin> op = adminRepo.findById(adminId);
		if(op.isPresent())
		{
			return op.get();
		}
		return null;
	}

	public Admin updateAdmin(int adminId, Admin admin) {
		Optional<Admin> op = adminRepo.findById(adminId);
		
		if(op!=null) {
			admin.setAdminId(adminId);
			return adminRepo.save(admin);
		}
		return null;
	}

	public Admin deleteAdmin(int adminId) {
		Optional<Admin> op = adminRepo.findById(adminId);
		if(op!= null) {
			Admin a = op.get();
			adminRepo.delete(a);
			return a;
		}
		
		return null;
	}
}
