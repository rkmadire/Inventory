package com.otsi.retail.barcode.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.otsi.retail.barcode.exceptions.RecordNotFoundException;
import com.otsi.retail.barcode.mapper.BarcodeMapper;
import com.otsi.retail.barcode.model.Barcode;
import com.otsi.retail.barcode.repo.BarcodeRepo;
import com.otsi.retail.barcode.vo.BarcodeVo;

/**
 * @author vasavi
 *
 */
@Component
public class BarcodeServiceImpl implements BarcodeService {

	@Autowired
	private BarcodeRepo barcodeRepo;

	@Autowired
	private BarcodeMapper barcodemapper;

	@Override
	public BarcodeVo createBarcode(BarcodeVo vo) {
		if (barcodeRepo.existsByBarcode(vo.getBarcode())) {
			throw new RuntimeException("barcode is already exists:" + vo.getBarcode());
		}
		Barcode bar = barcodemapper.VoToEntity(vo);

		BarcodeVo barcodesave = barcodemapper.EntityToVo(barcodeRepo.save(bar));
		return barcodesave;
	}

	@Override
	public Optional<Barcode> getBarcode(String barcode) {
		Optional<Barcode> vo = barcodeRepo.findByBarcode(barcode);
		if (!(vo.isPresent())) {
			throw new RecordNotFoundException("barcode record is not found");
		}

		return vo;
	}

}
