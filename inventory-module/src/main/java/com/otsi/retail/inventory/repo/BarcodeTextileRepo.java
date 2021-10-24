package com.otsi.retail.inventory.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.otsi.retail.inventory.model.BarcodeTextile;
import com.otsi.retail.inventory.vo.BarcodeTextileVo;

@Repository
public interface BarcodeTextileRepo extends JpaRepository<BarcodeTextile, Long> {

	Optional<BarcodeTextile> findByBarcode(String barcode);

	BarcodeTextile save(BarcodeTextileVo prodInv);

}
