package com.qsp.OnlinePharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qsp.OnlinePharmacy.dto.StaffDto;
import com.qsp.OnlinePharmacy.entity.Staff;
import com.qsp.OnlinePharmacy.service.StaffService;
import com.qsp.OnlinePharmacy.util.ResponseStructure;

@RestController
public class StaffController {

	@Autowired
	private StaffService staffService;
	
	public ResponseEntity<ResponseStructure<StaffDto>> addStaff(@RequestParam int adminId, @RequestParam int storeId, @RequestBody Staff staff){
		return staffService.addStaff(adminId, storeId, staff);
	}
	
	@PutMapping
	public ResponseEntity<ResponseStructure<StaffDto>> updateStaff(@RequestParam int staffId,@RequestBody Staff staff){
		return staffService.updateStaff(staffId,staff);
	}
	@GetMapping
	public ResponseEntity<ResponseStructure<StaffDto>> findStaff(@RequestParam int staffId){
		return staffService.getStaffById(staffId);
	}
	@DeleteMapping
	public ResponseEntity<ResponseStructure<StaffDto>> deleteStaffById(@RequestParam int staffId){
		return staffService.deleteStaffById(staffId);
	}

}
