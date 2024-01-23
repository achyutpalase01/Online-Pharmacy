package com.qsp.OnlinePharmacy.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qsp.OnlinePharmacy.entity.Address;
import com.qsp.OnlinePharmacy.repository.AddressRepo;

@Repository
public class AddressDao {

	@Autowired
	private AddressRepo addressRepo;

	public Address saveAddress(Address address) {
		return addressRepo.save(address);
	}

	public Address findAddressById(int addressId) {
		Optional<Address> add = addressRepo.findById(addressId);
		if (add.isPresent()) {
			return add.get();
		}
		return null;
	}

	public Address updateAddress(int addressId, Address address) {
		Optional<Address> add = addressRepo.findById(addressId);
		if (add.isPresent()) {
			address.setAddressId(addressId);
			return addressRepo.save(address);
		}
		return null;
	}

	public Address deleteAddress(int addressId) {
		Optional<Address> add = addressRepo.findById(addressId);
		if (add != null) {
			Address a = add.get();
			addressRepo.delete(a);
			return a;
		}
		return null;
	}

}
