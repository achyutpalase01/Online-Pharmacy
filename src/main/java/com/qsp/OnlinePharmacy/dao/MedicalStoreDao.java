package com.qsp.OnlinePharmacy.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qsp.OnlinePharmacy.entity.MedicalStore;
import com.qsp.OnlinePharmacy.repository.MedicalStoreRepo;

@Repository
public class MedicalStoreDao {

	@Autowired
	private MedicalStoreRepo medicalStoreRepo;
	
	public MedicalStore saveMedicalStore(MedicalStore medicalStore) {
		
		return medicalStoreRepo.save(medicalStore);
	}

	public MedicalStore findById(int storeId) {
		Optional<MedicalStore> op = medicalStoreRepo.findById(storeId);
		if(op.isPresent()) {
			MedicalStore m = op.get();
			return m;
		}
		return null;
	}
	
	public MedicalStore updateMedicalStore(int storeId, MedicalStore medicalStore) {
//		medicalstore=name,managername ,address
		Optional<MedicalStore> optional=medicalStoreRepo.findById(storeId);
	    if(optional.isPresent()) {
	    	MedicalStore oldMedicalStore=optional.get();
	    	medicalStore.setStoreId(storeId);
//	    	medicalstore=id,name,managername ,address
	    	medicalStore.setAdmin(oldMedicalStore.getAdmin());
	    	medicalStore.setAddress(oldMedicalStore.getAddress());
//	    	medicalstore is havig id,name,managername,phone,admin address
	    	return medicalStoreRepo.save(medicalStore);
	    	
	    }
		return null;
	}

	public MedicalStore deleteMedicalstoreById(int storeId) {
		Optional<MedicalStore> optional=medicalStoreRepo.findById(storeId);
		if(optional.isPresent()){
			 MedicalStore medicalStore=optional.get();
			 medicalStoreRepo.delete(medicalStore);
			 return medicalStore;
		}
		return null;
	}
}
