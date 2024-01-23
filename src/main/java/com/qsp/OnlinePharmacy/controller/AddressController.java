package com.qsp.OnlinePharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qsp.OnlinePharmacy.dto.AddressDto;
import com.qsp.OnlinePharmacy.entity.Address;
import com.qsp.OnlinePharmacy.service.AddressService;
import com.qsp.OnlinePharmacy.util.ResponseStructure;

@Controller
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private AddressService addressService;

	@PostMapping
	public ResponseEntity<ResponseStructure<AddressDto>> saveAddress(@RequestBody Address address) {
		return addressService.saveAddress(address);
	}

	@GetMapping
	public ResponseEntity<ResponseStructure<AddressDto>> findAddressById(@RequestParam int addressId) {
		return addressService.findAddressById(addressId);
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<AddressDto>> updateAddress(@RequestParam int addressId,
			@RequestBody Address address) {
		return addressService.updateAddress(addressId, address);
	}

	@DeleteMapping
	public ResponseEntity<ResponseStructure<AddressDto>> deleteAddress(@RequestParam int addressId) {
		return addressService.deleteAddress(addressId);
	}
}