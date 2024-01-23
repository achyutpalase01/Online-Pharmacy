package com.qsp.OnlinePharmacy.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.qsp.OnlinePharmacy.dao.AddressDao;
import com.qsp.OnlinePharmacy.dao.CustomerDao;
import com.qsp.OnlinePharmacy.dto.AddressDto;
import com.qsp.OnlinePharmacy.dto.BookingDto;
import com.qsp.OnlinePharmacy.dto.CustomerDto;
import com.qsp.OnlinePharmacy.entity.Address;
import com.qsp.OnlinePharmacy.entity.Booking;
import com.qsp.OnlinePharmacy.entity.Customer;
import com.qsp.OnlinePharmacy.exception.AddressAlreadymappedtoCustomer;
import com.qsp.OnlinePharmacy.exception.AddressMappedToMedicalStore;
import com.qsp.OnlinePharmacy.exception.EmailNotFoundException;
import com.qsp.OnlinePharmacy.exception.IdNotFoundException;
import com.qsp.OnlinePharmacy.exception.InvalidPasswordException;
import com.qsp.OnlinePharmacy.exception.PhoneNumberNotValidException;
import com.qsp.OnlinePharmacy.util.ResponseStructure;

@Service
public class CustomerService {

	@Autowired
	private CustomerDao custmerDao;
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<ResponseStructure<CustomerDto>> addCustomer(int addressId, Customer customer) {
		Address dbAddress = addressDao.findAddressById(addressId);
//		when addressId =100 your db address==null
//		dbAddress==is having id name also ereltionship entity that is your customer id=5

//		checking addressId is present or not
		if (dbAddress != null) {
			if (dbAddress.getMedicalStore() != null) {
				throw new AddressMappedToMedicalStore("sorry this address is mapped to medicalstore");
			}
			if (dbAddress.getCustomer() != null) {
				throw new AddressAlreadymappedtoCustomer("sorry this address is mapped to other customer");
			}
			dbAddress.setCustomer(customer);
//			yes addressId is present
			List<Address> addresses = new ArrayList<Address>();
			addresses.add(dbAddress);
//			customer=only the own attributes not a relationship attributes
			customer.setAddresses(addresses);
//			now customer is having adddress also
			Customer dbCustomer = custmerDao.saveCustomer(customer);
//	dbCustomer is having its own attributes then relationship attributes that is List address and List of bookings but its null
//			but list  of address is present
//			Copy Customer to customerDto
//			but this customer dto is having ListofaddresssesDto and ListOfBooking dto but still it is null;
//			copy List of Addressto addressDto
			CustomerDto customerDto = this.modelMapper.map(dbCustomer, CustomerDto.class);
			List<AddressDto> addressDtos;
			for (Address address : dbCustomer.getAddresses()) {
				AddressDto addressDto = this.modelMapper.map(address, AddressDto.class);
				addressDtos = new ArrayList<AddressDto>();
				addressDtos.add(addressDto);
				customerDto.setAddresses(addressDtos);
			}
			customerDto.setBookingDtos(null);
			ResponseStructure<CustomerDto> structure = new ResponseStructure<CustomerDto>();
			structure.setMsg("CustometrAddedsuccessfully");
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setData(customerDto);
			return new ResponseEntity<ResponseStructure<CustomerDto>>(structure, HttpStatus.CREATED);

		} else {
			throw new IdNotFoundException("Sorry failed to add customer");
		}
	}

	public ResponseEntity<ResponseStructure<CustomerDto>> updateCustomer(int customerId, Customer customer) {
		Customer dbCustomer = custmerDao.updateCustomer(customerId, customer);
		if (dbCustomer != null) {
			CustomerDto customerDto = this.modelMapper.map(dbCustomer, CustomerDto.class);
			List<AddressDto> addressDtos;
			for (Address address : dbCustomer.getAddresses()) {
				AddressDto addressDto = this.modelMapper.map(address, AddressDto.class);
				addressDtos = new ArrayList<AddressDto>();
				addressDtos.add(addressDto);
				customerDto.setAddresses(addressDtos);
			}
			customerDto.setBookingDtos(null);
			ResponseStructure<CustomerDto> structure = new ResponseStructure<CustomerDto>();
			structure.setMsg(null);
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setData(customerDto);
			return new ResponseEntity<ResponseStructure<CustomerDto>>(structure, HttpStatus.CREATED);
		} else {
			throw new IdNotFoundException("Sorry failed to update Customer");
		}
	}

	public ResponseEntity<ResponseStructure<CustomerDto>> getCustomerById(int customerId) {
		Customer dbCustomer = custmerDao.getCustomerById(customerId);
		if (dbCustomer != null) {
			CustomerDto customerDto = this.modelMapper.map(dbCustomer, CustomerDto.class);
			List<AddressDto> addressDtos;
			for (Address address : dbCustomer.getAddresses()) {
				AddressDto addressDto = this.modelMapper.map(address, AddressDto.class);
				addressDtos = new ArrayList<AddressDto>();
				addressDtos.add(addressDto);
				customerDto.setAddresses(addressDtos);
			}
			customerDto.setBookingDtos(null);
			ResponseStructure<CustomerDto> structure = new ResponseStructure<CustomerDto>();
			structure.setMsg("Customer data fetched successfully");
			structure.setStatus(HttpStatus.FOUND.value());
			structure.setData(customerDto);
			return new ResponseEntity<ResponseStructure<CustomerDto>>(structure, HttpStatus.FOUND);
		} else {
			throw new IdNotFoundException("Sorry failed to fetch Customer");
		}
	}

	public ResponseEntity<ResponseStructure<CustomerDto>> deleteCustomerById(int customerId) {
		Customer dbCustomer = custmerDao.deleteCustomerById(customerId);
		if (dbCustomer != null) {
			CustomerDto customerDto = this.modelMapper.map(dbCustomer, CustomerDto.class);
			List<AddressDto> addressDtos;
			for (Address address : dbCustomer.getAddresses()) {
				AddressDto addressDto = this.modelMapper.map(address, AddressDto.class);
				addressDtos = new ArrayList<AddressDto>();
				addressDtos.add(addressDto);
				customerDto.setAddresses(addressDtos);
			}
			customerDto.setBookingDtos(null);
			ResponseStructure<CustomerDto> structure = new ResponseStructure<CustomerDto>();
			structure.setMsg("Customer data deleted successfully");
			structure.setStatus(HttpStatus.GONE.value());
			structure.setData(customerDto);
			return new ResponseEntity<ResponseStructure<CustomerDto>>(structure, HttpStatus.GONE);
		} else {
			throw new IdNotFoundException("Sorry failed to delete Customer");
		}
	}

	public ResponseEntity<ResponseStructure<CustomerDto>> loginCustomer(String email, String password) {
		Customer dbCustomer = custmerDao.findByEmail(email);
		if (dbCustomer != null) {
			if (dbCustomer.getPassword().equals(password)) {
//				login success
				CustomerDto customerDto = this.modelMapper.map(dbCustomer, CustomerDto.class);
				List<AddressDto> addressDtos;
				for (Address address : dbCustomer.getAddresses()) {
					AddressDto addressDto = this.modelMapper.map(address, AddressDto.class);
					addressDtos = new ArrayList<AddressDto>();
					addressDtos.add(addressDto);
					customerDto.setAddresses(addressDtos);
				}
				List<BookingDto> bookingDtos;
				for (Booking booking : dbCustomer.getBookings()) {
					BookingDto bookingDto = this.modelMapper.map(booking, BookingDto.class);
					bookingDtos = new ArrayList<BookingDto>();
					bookingDtos.add(bookingDto);
					customerDto.setBookingDtos(bookingDtos);
				}

				ResponseStructure<CustomerDto> structure = new ResponseStructure<CustomerDto>();
				structure.setMsg("Customer Login Success WELCOME TO ONLINEPHARMACY");
				structure.setStatus(HttpStatus.OK.value());
				structure.setData(customerDto);
				return new ResponseEntity<ResponseStructure<CustomerDto>>(structure, HttpStatus.OK);

			} else {
//				invalid password
				throw new InvalidPasswordException("Sorry failed to Login");
			}

		} else {
			throw new EmailNotFoundException("Sorry Failed to Login");
		}
	}

	public ResponseEntity<ResponseStructure<CustomerDto>> forgotPassword(String email, long phone, String password) {
		Customer customer = custmerDao.findByEmail(email);
		if (customer != null) {
//			customer is present
			if (customer.getPhoneNumber() == phone) {
//				phone is registered
				customer.setPassword(password);
				Customer dbCustomer = custmerDao.updateCustomer(customer.getCustomerId(), customer);

				CustomerDto customerDto = this.modelMapper.map(dbCustomer, CustomerDto.class);
				List<AddressDto> addressDtos;
				for (Address address : dbCustomer.getAddresses()) {
					AddressDto addressDto = this.modelMapper.map(address, AddressDto.class);
					addressDtos = new ArrayList<AddressDto>();
					addressDtos.add(addressDto);
					customerDto.setAddresses(addressDtos);
				}
				List<BookingDto> bookingDtos;
				for (Booking booking : dbCustomer.getBookings()) {
					BookingDto bookingDto = this.modelMapper.map(booking, BookingDto.class);
					bookingDtos = new ArrayList<BookingDto>();
					bookingDtos.add(bookingDto);
					customerDto.setBookingDtos(bookingDtos);
				}

				ResponseStructure<CustomerDto> structure = new ResponseStructure<CustomerDto>();
				structure.setMsg("Customer Pssword reset successfully");
				structure.setStatus(HttpStatus.OK.value());
				structure.setData(customerDto);
				return new ResponseEntity<ResponseStructure<CustomerDto>>(structure, HttpStatus.OK);

			} else {
				throw new PhoneNumberNotValidException(
						"Sorry failed to reset password Please enter Registered PhoneNumber");
			}
		} else {
			throw new EmailNotFoundException("Sorry failed to reset password Please enter valid Email");
		}
	}
}
