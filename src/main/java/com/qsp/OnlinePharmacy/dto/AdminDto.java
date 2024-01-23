package com.qsp.OnlinePharmacy.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AdminDto {
	private int adminId;
	private String adminName;
	private String adminEmail;
	private long phoneNumber;
}