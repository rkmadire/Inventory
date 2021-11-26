package com.otsi.retail.barcode.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.otsi.retail.barcode.model.Barcode;

@Repository
public interface BarcodeRepo extends JpaRepository<Barcode, Long>{

	Optional<Barcode> findByBarcode(String barcode);

	boolean existsByBarcode(String barcode);

}

