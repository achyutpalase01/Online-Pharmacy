package com.qsp.OnlinePharmacy.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.qsp.OnlinePharmacy.dao.AdminDao;
import com.qsp.OnlinePharmacy.dto.AdminDto;
import com.qsp.OnlinePharmacy.entity.Admin;
import com.qsp.OnlinePharmacy.exception.IdNotFoundException;
import com.qsp.OnlinePharmacy.util.ResponseStructure;

@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private ModelMapper mapper;

	public ResponseEntity<ResponseStructure<AdminDto>> saveAdmin(Admin admin) {

		Admin db = adminDao.saveAdmin(admin);

		AdminDto dto = this.mapper.map(db, AdminDto.class);

		ResponseStructure<AdminDto> structure = new ResponseStructure<>();
		structure.setMsg("Admin Ssaved Successfully");
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setData(dto);

		return new ResponseEntity<ResponseStructure<AdminDto>>(structure, HttpStatus.CREATED);

	}

	public ResponseEntity<ResponseStructure<AdminDto>> getAdmin(int adminId) {
		Admin db = adminDao.getAdmin(adminId);
		if (db != null) {
			AdminDto dto = this.mapper.map(db, AdminDto.class);
			ResponseStructure<AdminDto> structure = new ResponseStructure<>();
			structure.setMsg("Admin Found Successfully");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(dto);

			return new ResponseEntity<ResponseStructure<AdminDto>>(structure, HttpStatus.FOUND);
		}
		throw new IdNotFoundException("Sorry Failed to Find the Admin");
	}

	public ResponseEntity<ResponseStructure<AdminDto>> updateAdmin(int adminId, Admin admin) {
		Admin db = adminDao.updateAdmin(adminId, admin);
		if (db != null) {
			AdminDto dto = this.mapper.map(db, AdminDto.class);
			ResponseStructure<AdminDto> structure = new ResponseStructure<>();
			structure.setMsg("Admin Updated Successfully");
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setData(dto);

			return new ResponseEntity<ResponseStructure<AdminDto>>(structure, HttpStatus.CREATED);
		}
		throw new IdNotFoundException("Sorry Failed to Find the Admin");
	}

	public ResponseEntity<ResponseStructure<AdminDto>> deleteAdmin(int adminId) {
		Admin db = adminDao.deleteAdmin(adminId);
		if (db != null) {
			AdminDto dto = this.mapper.map(db, AdminDto.class);
			ResponseStructure<AdminDto> structure = new ResponseStructure<>();
			structure.setMsg("Admin Deleted Successfully");
			structure.setStatus(HttpStatus.GONE.value());
			structure.setData(dto);

			return new ResponseEntity<ResponseStructure<AdminDto>>(structure, HttpStatus.GONE);
		}
		throw new IdNotFoundException("Sorry Failed to Find the Admin");
	}
}