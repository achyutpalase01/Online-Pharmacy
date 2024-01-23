package com.qsp.OnlinePharmacy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddressAlreadymappedtoCustomer extends RuntimeException {

private String msg;
	
	public String getMessage() {
		return msg;
	}
}
