package com.qsp.OnlinePharmacy.dto;

import java.util.List;

import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerDto {
	
	private int customerId;
	private String customername;
	private String email;
	private long phoneNumber;
	
	@OneToMany
	private List<AddressDto> addresses;
	
	@OneToMany
	private List<BookingDto> bookingDtos;
}
