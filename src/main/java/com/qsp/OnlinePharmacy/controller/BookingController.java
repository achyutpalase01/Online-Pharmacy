package com.qsp.OnlinePharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qsp.OnlinePharmacy.dto.BookingDto;
import com.qsp.OnlinePharmacy.entity.Booking;
import com.qsp.OnlinePharmacy.service.BookingService;
import com.qsp.OnlinePharmacy.util.ResponseStructure;

@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	private BookingService service;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Booking>> addBooking(@RequestParam int customerId,
			@RequestParam int[] medicineId,@RequestBody BookingDto bookingDto){
		return service.addBooking(customerId,medicineId,bookingDto);
	}
	@DeleteMapping
	public ResponseEntity<ResponseStructure<Booking>> cancelBooking(@RequestParam int bookingId){
		return service.cancelBooking(bookingId);
	}
}
