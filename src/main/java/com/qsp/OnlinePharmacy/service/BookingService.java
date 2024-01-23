package com.qsp.OnlinePharmacy.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.qsp.OnlinePharmacy.dao.BookingDao;
import com.qsp.OnlinePharmacy.dao.CustomerDao;
import com.qsp.OnlinePharmacy.dao.MedicineDao;
import com.qsp.OnlinePharmacy.dto.BookingDto;
import com.qsp.OnlinePharmacy.entity.Booking;
import com.qsp.OnlinePharmacy.entity.Customer;
import com.qsp.OnlinePharmacy.entity.Medicine;
import com.qsp.OnlinePharmacy.enums.BookingStatus;
import com.qsp.OnlinePharmacy.exception.BookingAlreadyCancelled;
import com.qsp.OnlinePharmacy.exception.BookingCantCancelledNow;
import com.qsp.OnlinePharmacy.exception.BookingDeliveredException;
import com.qsp.OnlinePharmacy.exception.IdNotFoundException;
import com.qsp.OnlinePharmacy.util.ResponseStructure;

@Service
public class BookingService {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private BookingDao bookingDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private MedicineDao medicineDao;

	public ResponseEntity<ResponseStructure<Booking>> addBooking(int customerId, int[] medicineId,
			BookingDto bookingDto) {
		Booking booking = this.modelMapper.map(bookingDto, Booking.class);
		Customer dbCustomer = customerDao.getCustomerById(customerId);
		if (dbCustomer != null) {
//			customer is present
			booking.setCustomer(dbCustomer);
			List<Medicine> medicines = new ArrayList<Medicine>();
			for (int medId : medicineId) {
				Medicine dbMedicine = medicineDao.getMedicineByid(medId);
				if (dbMedicine != null) {
					medicines.add(dbMedicine);
//					updating stock quantity
					dbMedicine.setStockQuantity(dbMedicine.getStockQuantity() - booking.getQuantity());
				} else {
					throw new IdNotFoundException("sorry failed to add booking");
				}
			}
			booking.setMedicines(medicines);
//            update CustomerAlso
			List<Booking> bookings = new ArrayList<Booking>();
			bookings.add(booking);
			dbCustomer.setBookings(bookings);
			customerDao.updateCustomer(customerId, dbCustomer);
//			im adding booking status
			booking.setBookingStatus(BookingStatus.ACTIVE);
			Booking dbBooking = bookingDao.saveBooking(booking);

			ResponseStructure<Booking> structure = new ResponseStructure<Booking>();
			structure.setMsg("Booking added successfully");
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setData(dbBooking);
			return new ResponseEntity<ResponseStructure<Booking>>(structure, HttpStatus.CREATED);

		} else {
			throw new IdNotFoundException("Sorry failed to add booking");
		}
	}

	public ResponseEntity<ResponseStructure<Booking>> cancelBooking(int bookingId) {
		Booking dbBooking = bookingDao.findBookingById(bookingId);

//		Expected date=24
//		cantcancelleddate=24-2=22;

		if (dbBooking != null) {
			LocalDate cantcancelledday = dbBooking.getExpectedDate().minusDays(2);
			if (dbBooking.getBookingStatus().equals(BookingStatus.CANCELLED)) {
				throw new BookingAlreadyCancelled("sorry this booking already Cancelled");
			} else if (dbBooking.getBookingStatus().equals(BookingStatus.DELIVERED)) {
				throw new BookingDeliveredException("Sorry can't cancel Booking, its already delivered");
			} else if (LocalDate.now().equals(cantcancelledday) || LocalDate.now().isAfter(cantcancelledday)) {
				throw new BookingCantCancelledNow("Sorry booking can't cancelled Now");
			} else {
				Booking cancelledBooking = bookingDao.cancelBooking(bookingId);
				ResponseStructure<Booking> structure = new ResponseStructure<Booking>();
				structure.setMsg("Booking cancelled Successfully");
				structure.setStatus(HttpStatus.OK.value());
				structure.setData(cancelledBooking);
				return new ResponseEntity<ResponseStructure<Booking>>(structure, HttpStatus.OK);
			}

		} else {
			throw new IdNotFoundException("Sorry failed to cancel booking");
		}

	}
}
