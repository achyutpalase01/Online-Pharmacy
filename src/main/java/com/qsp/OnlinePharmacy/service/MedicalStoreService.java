package com.qsp.OnlinePharmacy.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.qsp.OnlinePharmacy.dao.AddressDao;
import com.qsp.OnlinePharmacy.dao.AdminDao;
import com.qsp.OnlinePharmacy.dao.MedicalStoreDao;
import com.qsp.OnlinePharmacy.dto.AddressDto;
import com.qsp.OnlinePharmacy.dto.AdminDto;
import com.qsp.OnlinePharmacy.dto.MedicalStoreDto;
import com.qsp.OnlinePharmacy.entity.Address;
import com.qsp.OnlinePharmacy.entity.Admin;
import com.qsp.OnlinePharmacy.entity.MedicalStore;
import com.qsp.OnlinePharmacy.exception.AddressAlreadymappedtoCustomer;
import com.qsp.OnlinePharmacy.exception.AddressMappedToMedicalStore;
import com.qsp.OnlinePharmacy.exception.IdNotFoundException;
import com.qsp.OnlinePharmacy.util.ResponseStructure;

@Service
public class MedicalStoreService {
	@Autowired
	private MedicalStoreDao storeDao;
	@Autowired 
	private ModelMapper modelMapper;
	@Autowired
	private AdminDao adminDao;
	@Autowired
	private AddressDao addressDao;

	public ResponseEntity<ResponseStructure<MedicalStoreDto>> saveMedicalStore(int adminId, int addressId,
			MedicalStoreDto medicalStoreDto) {
	
		MedicalStore medicalStore=this.modelMapper.map(medicalStoreDto,MedicalStore.class);
//		this medicalstore is not having admin and address yet
//		so first i need to get that admin
		Admin dbAdmin=adminDao.getAdmin(adminId);
//		im checking whether this admin is present or not
	
		if(dbAdmin!=null) {
			  medicalStore.setAdmin(dbAdmin);
		       Address dbAddress= addressDao.findAddressById(addressId);
		       if(dbAddress!=null) {
		    	   if(dbAddress.getCustomer()!=null) {
		    		   throw new AddressAlreadymappedtoCustomer("Sorry that address is mapped to customer so please give other address");
		    	   }
		    	   if(dbAddress.getMedicalStore()!=null) {
		    		   throw new AddressMappedToMedicalStore("Sorry address mapped to other medical store");
		    	   }
		    	   medicalStore.setAddress(dbAddress);
//		    	   update
		    	   dbAddress.setMedicalStore(medicalStore);
		    	   MedicalStore dbMedicalStore=storeDao.saveMedicalStore(medicalStore);
		    	  Address dbMedicalStoreAddress=dbMedicalStore.getAddress();
		    	  AddressDto addressDto=this.modelMapper.map(dbMedicalStoreAddress, AddressDto.class);
		    	  Admin dbMedicalStoreAdmin=dbMedicalStore.getAdmin();
		    	   
		    	   MedicalStoreDto dto=this.modelMapper.map(dbMedicalStore, MedicalStoreDto.class);
		    	   dto.setAddressDto(addressDto);
		           dto.setAdminDto(this.modelMapper.map(dbMedicalStoreAdmin, AdminDto.class));
		    	   ResponseStructure<MedicalStoreDto> structure=new ResponseStructure<MedicalStoreDto>();
		    	   structure.setMsg("MedicalStore added successfully");
		    	   structure.setStatus(HttpStatus.CREATED.value());
		    	   structure.setData(dto);
		    	   return new ResponseEntity<ResponseStructure<MedicalStoreDto>>(structure,HttpStatus.CREATED);
		  
		       }else {
		    	   throw new IdNotFoundException("Sorry failed to add medicalstore");
		       }
      
			
		}else {
			throw new IdNotFoundException("Sorry failed to add medicalstore");
		}
	}
	
	public ResponseEntity<ResponseStructure<MedicalStoreDto>> getMedicalStoreById(int storeId) {
		MedicalStore dbMedicalStore=storeDao.findById(storeId);
		if(dbMedicalStore!=null) {
			MedicalStoreDto storeDto=this.modelMapper.map(dbMedicalStore, MedicalStoreDto.class);
			storeDto.setAddressDto(this.modelMapper.map(dbMedicalStore.getAddress(), AddressDto.class));
			storeDto.setAdminDto(this.modelMapper.map(dbMedicalStore.getAdmin(), AdminDto.class));
			
			ResponseStructure<MedicalStoreDto> structure=new ResponseStructure<MedicalStoreDto>();
			structure.setMsg("MedicalStore fetched successfully");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(storeDto);
			return new ResponseEntity<ResponseStructure<MedicalStoreDto>>(structure,HttpStatus.FOUND);
		}else {
//			when that id is not present
			throw new IdNotFoundException("Sorry failed to fetch medicalstore");
		}	
	}

	public ResponseEntity<ResponseStructure<MedicalStoreDto>> updateMedicalStore(int storeId,
			MedicalStore medicalStore) {
//		medicalstore=name,manager name,phone
		MedicalStore dbMedicalStore=storeDao.updateMedicalStore(storeId,medicalStore);
		if(dbMedicalStore!=null) {
			MedicalStoreDto storeDto=this.modelMapper.map(dbMedicalStore, MedicalStoreDto.class);
			storeDto.setAddressDto(this.modelMapper.map(dbMedicalStore.getAddress(), AddressDto.class));
			storeDto.setAdminDto(this.modelMapper.map(dbMedicalStore.getAdmin(), AdminDto.class));
			
			ResponseStructure<MedicalStoreDto> structure=new ResponseStructure<MedicalStoreDto>();
			structure.setMsg("MedicalStoreUpdated successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(storeDto);
			return new ResponseEntity<ResponseStructure<MedicalStoreDto>>(structure,HttpStatus.OK);
		}else {
//			when that id is not present
			throw new IdNotFoundException("Sorry failed to update medicalstore");
		}
	
	
	}
	

	public ResponseEntity<ResponseStructure<MedicalStoreDto>> deleteMedicalstoreById(int storeId) {
	     MedicalStore dbMedicalStore=storeDao.deleteMedicalstoreById(storeId);
	 	if(dbMedicalStore!=null) {
			MedicalStoreDto storeDto=this.modelMapper.map(dbMedicalStore, MedicalStoreDto.class);
			storeDto.setAddressDto(this.modelMapper.map(dbMedicalStore.getAddress(), AddressDto.class));
			storeDto.setAdminDto(this.modelMapper.map(dbMedicalStore.getAdmin(), AdminDto.class));
			
			ResponseStructure<MedicalStoreDto> structure=new ResponseStructure<MedicalStoreDto>();
			structure.setMsg("MedicalStore deleted successfully");
			structure.setStatus(HttpStatus.GONE.value());
			structure.setData(storeDto);
			return new ResponseEntity<ResponseStructure<MedicalStoreDto>>(structure,HttpStatus.GONE);
		}else {
//			when that id is not present
			throw new IdNotFoundException("Sorry failed to delete medicalstore");
		}
	}
}