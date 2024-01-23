package com.qsp.OnlinePharmacy.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerId;
	private String customername;
	private String email;
	private String password;
	private long phoneNumber;
	
	@OneToMany
	private List<Address> addresses;
	
	@OneToMany(mappedBy = "customer")
	private List<Booking> bookings;
}
