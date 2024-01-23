package com.qsp.OnlinePharmacy.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.qsp.OnlinePharmacy.dao.AddressDao;
import com.qsp.OnlinePharmacy.dto.AddressDto;
import com.qsp.OnlinePharmacy.entity.Address;
import com.qsp.OnlinePharmacy.exception.IdNotFoundException;
import com.qsp.OnlinePharmacy.util.ResponseStructure;

@Service
public class AddressService {

	@Autowired
	private AddressDao addressDao;

	@Autowired
	private ModelMapper mapper;
	
	public ResponseEntity<ResponseStructure<AddressDto>> saveAddress(Address address) {
		Address dbAddress = addressDao.saveAddress(address);
		AddressDto dto =  this.mapper.map(dbAddress, AddressDto.class);
		
		ResponseStructure<AddressDto> structure = new ResponseStructure<AddressDto>();
		structure.setMsg("Address Saved Successfully");
		structure.setStatus(HttpStatus.CREATED.value());
		structure.setData(dto);
		return new ResponseEntity<ResponseStructure<AddressDto>>(structure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<AddressDto>> findAddressById(int addressId) {
		Address dbAddress = addressDao.findAddressById(addressId);
		if (dbAddress != null) {
			AddressDto dto =  this.mapper.map(dbAddress, AddressDto.class);

			ResponseStructure<AddressDto> structure = new ResponseStructure<AddressDto>();
			structure.setMsg("Address Found Successfully");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(dto);
			return new ResponseEntity<ResponseStructure<AddressDto>>(structure, HttpStatus.FOUND);
		} else {
			throw new IdNotFoundException("Sorry Failed to Find the Address");
		}
	}

	public ResponseEntity<ResponseStructure<AddressDto>> updateAddress(int addressId, Address address) {
		Address dbAddress = addressDao.updateAddress(addressId, address);
		if (dbAddress != null) {
			AddressDto dto =  this.mapper.map(dbAddress, AddressDto.class);
			
			ResponseStructure<AddressDto> structure = new ResponseStructure<AddressDto>();
			structure.setMsg("Address updated Successfully");
			structure.setStatus(HttpStatus.OK.value());
			structure.setData(dto);
			return new ResponseEntity<ResponseStructure<AddressDto>>(structure, HttpStatus.OK);
		}
		throw new IdNotFoundException("Sorry Failed to Find the Address");
	}

	public ResponseEntity<ResponseStructure<AddressDto>> deleteAddress(int addressId) {
		Address dbAddress = addressDao.deleteAddress(addressId);
		if (dbAddress != null) {
			AddressDto dto =  this.mapper.map(dbAddress, AddressDto.class);
			
			ResponseStructure<AddressDto> structure = new ResponseStructure<AddressDto>();
			structure.setMsg("Address Deleted Successfully");
			structure.setStatus(HttpStatus.GONE.value());
			structure.setData(dto);
			return new ResponseEntity<ResponseStructure<AddressDto>>(structure, HttpStatus.GONE);
		}
		throw new IdNotFoundException("Sorry Failed to Find the Address");
	}
}