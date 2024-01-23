package com.qsp.OnlinePharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qsp.OnlinePharmacy.dto.AdminDto;
import com.qsp.OnlinePharmacy.entity.Admin;
import com.qsp.OnlinePharmacy.service.AdminService;
import com.qsp.OnlinePharmacy.util.ResponseStructure;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<AdminDto>> saveAdmin(@RequestBody Admin admin){
		return adminService.saveAdmin(admin);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<AdminDto>> getAdmin(@RequestParam int adminId){
		return adminService.getAdmin(adminId);
	}
	
	@PutMapping
	public ResponseEntity<ResponseStructure<AdminDto>> updateAdmin(@RequestParam int adminId, @RequestBody Admin admin){
		return adminService.updateAdmin(adminId, admin);
	}
	
	@DeleteMapping
	public ResponseEntity<ResponseStructure<AdminDto>> deleteAdmin(@RequestParam int adminId){
		return adminService.deleteAdmin(adminId);
	}
}
