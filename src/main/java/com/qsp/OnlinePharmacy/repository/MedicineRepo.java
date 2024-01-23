package com.qsp.OnlinePharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qsp.OnlinePharmacy.entity.Medicine;

public interface MedicineRepo extends JpaRepository<Medicine, Integer> {

}
