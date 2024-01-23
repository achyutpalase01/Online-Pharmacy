package com.qsp.OnlinePharmacy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IdNotFoundException extends RuntimeException {
	
	private String msg;
	
	public String getMessage() {
		return msg;
	}
	

}
