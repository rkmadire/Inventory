package com.otsi.retail.barcode.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.otsi.retail.barcode.model.Barcode;
import com.otsi.retail.barcode.vo.BarcodeVo;

@Service
public interface BarcodeService {

	BarcodeVo createBarcode(BarcodeVo vo);

	Optional<Barcode> getBarcode(String barcode);

}
