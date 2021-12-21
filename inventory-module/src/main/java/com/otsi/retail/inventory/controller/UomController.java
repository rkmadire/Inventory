package com.otsi.retail.inventory.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otsi.retail.inventory.gatewayresponse.GateWayResponse;
import com.otsi.retail.inventory.model.UomEntity;
import com.otsi.retail.inventory.service.UomService;
import com.otsi.retail.inventory.vo.UomVo;

@RestController
@RequestMapping("/uom")
public class UomController {

	private Logger log = LogManager.getLogger(UomController.class);

	@Autowired
	private UomService uomService;

	@PostMapping("/saveUom")
	public GateWayResponse<?> saveUom(@RequestBody UomVo vo) {
		log.info("Recieved request to saveUom:" + vo);
		UomVo uomSave = uomService.saveUom(vo);
		return new GateWayResponse<>("Uom  details saved successfully", uomSave);

	}

	@GetMapping("/getUom")
	public GateWayResponse<?> getUom(@RequestParam Long id) {
		log.info("Recieved request to getUom:" + id);
		Optional<UomEntity> uom = uomService.getUom(id);
		return new GateWayResponse<>("fetching uom details successfully with id", uom);
	}

	@GetMapping("/getAllUom")
	public GateWayResponse<?> getAllUom() {
		log.info("Recieved request to getAllUom");
		List<UomVo> allUoms = uomService.getAllUom();
		return new GateWayResponse<>("fetching all uom details sucessfully", allUoms);

	}

	@PutMapping("/updateUom")
	public GateWayResponse<?> updateUom(@RequestBody UomVo uomVo) throws Exception {
		log.info("Recieved request to updateUom:" + uomVo);
		String updateUom = uomService.updateUom(uomVo);
		return new GateWayResponse<>("updated uom data successfully", updateUom);

	}

	@DeleteMapping("/deleteUom")
	public GateWayResponse<?> deleteUom(@RequestParam("id") Long id) throws Exception {
		log.info("Recieved request to deleteUom:" + id);
		String deleteUom = uomService.deleteUom(id);
		return new GateWayResponse<>("domain data deleted successfully", deleteUom);

	}

}
