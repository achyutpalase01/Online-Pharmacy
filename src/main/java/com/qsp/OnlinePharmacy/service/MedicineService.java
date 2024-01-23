package com.qsp.OnlinePharmacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.qsp.OnlinePharmacy.dao.MedicalStoreDao;
import com.qsp.OnlinePharmacy.dao.MedicineDao;
import com.qsp.OnlinePharmacy.entity.MedicalStore;
import com.qsp.OnlinePharmacy.entity.Medicine;
import com.qsp.OnlinePharmacy.exception.IdNotFoundException;
import com.qsp.OnlinePharmacy.util.ResponseStructure;

@Service
public class MedicineService {

	@Autowired
	private MedicineDao medicineDao;
	@Autowired
	private MedicalStoreDao storeDao;

	public ResponseEntity<ResponseStructure<Medicine>> saveMedicine(int storeId, Medicine medicine) {
		MedicalStore dbMedicalStore = storeDao.findById(storeId);
		if (dbMedicalStore != null) {
			medicine.setMedicalStore(dbMedicalStore);
			Medicine dbMedicine = medicineDao.saveMedicine(medicine);

			ResponseStructure<Medicine> structure = new ResponseStructure<Medicine>();
			structure.setMsg("medicine added successfully");
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setData(dbMedicine);
			return new ResponseEntity<ResponseStructure<Medicine>>(structure, HttpStatus.CREATED);

		} else {
			throw new IdNotFoundException("Sorry failed to add medicine");
		}
	}

	public ResponseEntity<ResponseStructure<Medicine>> updateMedicine(int medicineId, Medicine medicine) {
		Medicine dbMedicine = medicineDao.updateMedicine(medicineId, medicine);
		if (dbMedicine != null) {
			ResponseStructure<Medicine> structure = new ResponseStructure<Medicine>();
			structure.setMsg("medicine updated successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(dbMedicine);
			return new ResponseEntity<ResponseStructure<Medicine>>(structure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Sorry failed to update Medicine");
		}
	}

	public ResponseEntity<ResponseStructure<Medicine>> getMedicine(int medicineId) {
		Medicine dbMedicine = medicineDao.getMedicineByid(medicineId);
		if (dbMedicine != null) {
			ResponseStructure<Medicine> structure = new ResponseStructure<Medicine>();
			structure.setMsg("medicine fetched successfully");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(dbMedicine);
			return new ResponseEntity<ResponseStructure<Medicine>>(structure, HttpStatus.FOUND);
		} else {
			throw new IdNotFoundException("Sorry failed to fetch Medicine");
		}
	}

	public ResponseEntity<ResponseStructure<Medicine>> deleteMedicine(int medicineId) {
		Medicine dbMedicine = medicineDao.deleteMedicineById(medicineId);
		if (dbMedicine != null) {
			ResponseStructure<Medicine> structure = new ResponseStructure<Medicine>();
			structure.setMsg("medicine deleted successfully");
			structure.setStatus(HttpStatus.GONE.value());
			structure.setData(dbMedicine);
			return new ResponseEntity<ResponseStructure<Medicine>>(structure, HttpStatus.GONE);
		} else {
			throw new IdNotFoundException("Sorry failed to delete Medicine");
		}
	}
}
