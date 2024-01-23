package com.qsp.OnlinePharmacy.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MedicalStoreDto {
	private int storeId;
	private String name;
	private String managerName;
	private long phone;
	
	private AdminDto adminDto;
	
	private AddressDto addressDto;
	
}
