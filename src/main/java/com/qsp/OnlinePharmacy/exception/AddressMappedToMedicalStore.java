package com.qsp.OnlinePharmacy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressMappedToMedicalStore extends RuntimeException {
	
private String msg;
	
	public String getMessage() {
		return msg;
	}
}
