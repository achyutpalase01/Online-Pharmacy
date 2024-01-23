package com.qsp.OnlinePharmacy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.qsp.OnlinePharmacy.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

	@Query("Select c from Customer c where c.email=?1")
	public Optional<Customer> findByEmail(String email);

}
