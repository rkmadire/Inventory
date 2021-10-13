package com.otsi.retail.inventory.controller;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.otsi.retail.inventory.model.Domaindata;
import com.otsi.retail.inventory.service.DomainDataService;
import com.otsi.retail.inventory.vo.DomainDataVo;

@RestController
@RequestMapping("/domaindata")
public class DomainDataController {

	private Logger log = LoggerFactory.getLogger(DomainDataController.class);

	@Autowired
	private DomainDataService domainDataService;

	@PostMapping("/saveDomainData")
	public GateWayResponse<?> saveDomainData(@RequestBody DomainDataVo vo) {
		log.info("Recieved request to saveDomainData:" + vo);
		DomainDataVo domainSave = domainDataService.saveDomainData(vo);
		return new GateWayResponse<>("Domain data saved successfully", domainSave);

	}

	@GetMapping("/getDomain")
	public GateWayResponse<?> getDomain(@RequestParam Long domainDataId) {
		log.info("Recieved request to getDomain:" + domainDataId);
		Optional<Domaindata> domain = domainDataService.getDomainDataById(domainDataId);
		return new GateWayResponse<>("fetching domain data details successfully with id", domain);
	}

	@GetMapping("/getAllDomainData")
	public GateWayResponse<?> getAllDomainData() {

		List<DomainDataVo> allDomains = domainDataService.getAllDomainData();
		return new GateWayResponse<>("fetching all domain data details sucessfully", allDomains);

	}

	@PutMapping("/updateDomaindata")
	public GateWayResponse<?> updateDomaindata(@RequestBody DomainDataVo domainVo) throws Exception {

		String updateDomain = domainDataService.updateDomainData(domainVo);
		return new GateWayResponse<>("updated domain data successfully", updateDomain);

	}

	@DeleteMapping("/deleteDomainData")
	public GateWayResponse<?> deleteDomainData(@RequestParam("domainDataId") Long domainDataId) throws Exception {

		String deleteDomain = domainDataService.deleteDomain(domainDataId);
		return new GateWayResponse<>("domain data deleted successfully", deleteDomain);

	}

}
