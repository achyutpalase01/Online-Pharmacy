package com.qsp.OnlinePharmacy.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.qsp.OnlinePharmacy.dao.AdminDao;
import com.qsp.OnlinePharmacy.dao.MedicalStoreDao;
import com.qsp.OnlinePharmacy.dao.StaffDao;
import com.qsp.OnlinePharmacy.dto.AdminDto;
import com.qsp.OnlinePharmacy.dto.MedicalStoreDto;
import com.qsp.OnlinePharmacy.dto.StaffDto;
import com.qsp.OnlinePharmacy.entity.Admin;
import com.qsp.OnlinePharmacy.entity.MedicalStore;
import com.qsp.OnlinePharmacy.entity.Staff;
import com.qsp.OnlinePharmacy.exception.IdNotFoundException;
import com.qsp.OnlinePharmacy.util.ResponseStructure;

@Service
public class StaffService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private StaffDao staffDao;
	@Autowired
	private MedicalStoreDao medicalStoreDao;
	@Autowired
	private AdminDao adminDao;

	public ResponseEntity<ResponseStructure<StaffDto>> addStaff(int adminId, int storeId, Staff staff) {
		MedicalStore dbMedicalStore=medicalStoreDao.findById(storeId);
		if(dbMedicalStore!=null) {
			staff.setMedicalStore(dbMedicalStore);
			Admin dbAdmin=adminDao.getAdmin(adminId);
			if(dbAdmin!=null) {
				staff.setAdmin(dbAdmin);
				Staff dbStaff=staffDao.saveStaff(staff);
				
				StaffDto dbStaffDto=this.modelMapper.map(dbStaff, StaffDto.class);
				dbStaffDto.setAdminDto(this.modelMapper.map(dbStaff.getAdmin(), AdminDto.class));
				dbStaffDto.setMedicalStoreDto(this.modelMapper.map(dbStaff.getMedicalStore(), MedicalStoreDto.class));
				
				ResponseStructure<StaffDto> structure=new ResponseStructure<StaffDto>();
				structure.setMsg("Staff Added successfully");
				structure.setStatus(HttpStatus.CREATED.value());
				structure.setData(dbStaffDto);
				return new ResponseEntity<ResponseStructure<StaffDto>>(structure,HttpStatus.CREATED);
				
			}else {
				throw new IdNotFoundException("Sorry failed to add staff");
			}
		
		}else {
			throw new IdNotFoundException("Sorry failed to add staff");
		}
	}

	public ResponseEntity<ResponseStructure<StaffDto>> updateStaff(int staffId, Staff staff) {
		Staff dbStaff=staffDao.updateStaff(staffId,staff);
		if(dbStaff!=null){
			StaffDto dbStaffDto=this.modelMapper.map(dbStaff, StaffDto.class);
			dbStaffDto.setAdminDto(this.modelMapper.map(dbStaff.getAdmin(), AdminDto.class));
			dbStaffDto.setMedicalStoreDto(this.modelMapper.map(dbStaff.getMedicalStore(), MedicalStoreDto.class));
			
			ResponseStructure<StaffDto> structure=new ResponseStructure<StaffDto>();
			structure.setMsg("Staff updated successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(dbStaffDto);
			return new ResponseEntity<ResponseStructure<StaffDto>>(structure,HttpStatus.OK);
	
		}else {
			throw new IdNotFoundException("sorry failed to update STaff");
		}

	}

	public ResponseEntity<ResponseStructure<StaffDto>> getStaffById(int staffId) {
	 Staff dbStaff=staffDao.getStaffById(staffId);
	 if(dbStaff!=null){
			StaffDto dbStaffDto=this.modelMapper.map(dbStaff, StaffDto.class);
			dbStaffDto.setAdminDto(this.modelMapper.map(dbStaff.getAdmin(), AdminDto.class));
			dbStaffDto.setMedicalStoreDto(this.modelMapper.map(dbStaff.getMedicalStore(), MedicalStoreDto.class));
			
			ResponseStructure<StaffDto> structure=new ResponseStructure<StaffDto>();
			structure.setMsg("Staff data fetched successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(dbStaffDto);
			return new ResponseEntity<ResponseStructure<StaffDto>>(structure,HttpStatus.OK);
	
		}else {
			throw new IdNotFoundException("sorry failed to get Staff");
		}

	}

	public ResponseEntity<ResponseStructure<StaffDto>> deleteStaffById(int staffId) {
		Staff dbStaff=staffDao.deleteStaffById(staffId);
		if(dbStaff!=null){
			StaffDto dbStaffDto=this.modelMapper.map(dbStaff, StaffDto.class);
			dbStaffDto.setAdminDto(this.modelMapper.map(dbStaff.getAdmin(), AdminDto.class));
			dbStaffDto.setMedicalStoreDto(this.modelMapper.map(dbStaff.getMedicalStore(), MedicalStoreDto.class));
			
			ResponseStructure<StaffDto> structure=new ResponseStructure<StaffDto>();
			structure.setMsg("Staff data deleted successfully");
			structure.setStatus(HttpStatus.GONE.value());
			structure.setData(dbStaffDto);
			return new ResponseEntity<ResponseStructure<StaffDto>>(structure,HttpStatus.GONE);
	
		}else {
			throw new IdNotFoundException("sorry failed to delete Staff");
		}
	}
}
