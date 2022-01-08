package com.otsi.retail.inventory.repo;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.otsi.retail.inventory.exceptions.InvalidDataException;
import com.otsi.retail.inventory.model.ProductTextile;

public class ProductTextileRepoImpl {

	@PersistenceContext
	private EntityManager em;

	public List<String> getUniqueColumn(String enumName) {
		String query = " select p." + enumName + " from  product_textile p group by  p." + enumName;
		try {
			Query query1 = em.createNativeQuery(query);
			List<String> result = query1.getResultList();
			return result;
		} catch (Exception ex) {
			throw new InvalidDataException("data is not correct");
		} finally {

			if (em.isOpen()) {
				em.close();
			}

		}
	}

}
