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

import com.qsp.OnlinePharmacy.dto.MedicalStoreDto;
import com.qsp.OnlinePharmacy.entity.MedicalStore;
import com.qsp.OnlinePharmacy.service.MedicalStoreService;
import com.qsp.OnlinePharmacy.util.ResponseStructure;

@RestController
@RequestMapping("/store")
public class MedicalStoreController {

	@Autowired
	private MedicalStoreService medicalStoreService;

	@PostMapping
	public ResponseEntity<ResponseStructure<MedicalStoreDto>> saveMedicalStore(@RequestParam int adminId,
			@RequestParam int addressId, @RequestBody MedicalStoreDto medicalStoreDto) {
		return medicalStoreService.saveMedicalStore(adminId, addressId, medicalStoreDto);
	}
	
	@PutMapping
	public ResponseEntity<ResponseStructure<MedicalStoreDto>> updateMedicalstore(@RequestParam int storeId,
			@RequestBody MedicalStore medicalStore){
		return medicalStoreService.updateMedicalStore(storeId,medicalStore);
//		medicalstore =name manager phone
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<MedicalStoreDto>> findMedicalStoreById(@RequestParam int storeId){
		return medicalStoreService.getMedicalStoreById(storeId);
	}
	@DeleteMapping
	public ResponseEntity<ResponseStructure<MedicalStoreDto>> deleteMedicalStoreById(@RequestParam int storeId){
		return medicalStoreService.deleteMedicalstoreById(storeId);
	}
}
