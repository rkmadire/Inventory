package com.otsi.retail.inventory.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.otsi.retail.inventory.model.Domaindata;

@Repository
public interface DomainDataRepo extends JpaRepository<Domaindata, Long> {

	Domaindata findByDomainDataId(Long domainDataId);

}
