package com.qsp.OnlinePharmacy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDeliveredException extends RuntimeException {

	private String msg;

	public String getMessage() {
		return msg;
	}

}
