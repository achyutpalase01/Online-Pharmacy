package com.qsp.OnlinePharmacy.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qsp.OnlinePharmacy.entity.Booking;
import com.qsp.OnlinePharmacy.enums.BookingStatus;
import com.qsp.OnlinePharmacy.repository.BookingRepo;

@Repository
public class BookingDao {

	@Autowired
	private BookingRepo repo;

	public Booking saveBooking(Booking booking) {
		// TODO Auto-generated method stub
		return repo.save(booking);
	}

	public Booking cancelBooking(int bookingId) {
		Optional<Booking> optional=repo.findById(bookingId);
		if(optional.isPresent()) {
		Booking booking=optional.get();
			booking.setBookingStatus(BookingStatus.CANCELLED);
			return repo.save(booking);
		}
		return null;
	}

	public Booking findBookingById(int bookingId) {
		Optional<Booking> optional=repo.findById(bookingId);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
}
