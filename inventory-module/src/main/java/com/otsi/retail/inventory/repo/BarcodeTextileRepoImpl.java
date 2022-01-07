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
import com.otsi.retail.inventory.model.BarcodeTextile;

public class BarcodeTextileRepoImpl implements JpaRepository<BarcodeTextile, Long> {

	@PersistenceContext
	private EntityManager em;

	public List<String> getUniqueColumn(String enumName) {
		String query = " select p." + enumName + " from  barcode_textile p group by  p." + enumName;
		try {

			Query query1 = em.createNativeQuery(query);
			List<String> result = query1.getResultList();
			// System.out.println(".............."+result.toString());
			return result;
		} catch (Exception ex) {
           throw  new InvalidDataException("data is not correct");
		} finally {

			if (em.isOpen()) {
				em.close();
			}

		}
	}

	@Override
	public Page<BarcodeTextile> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends BarcodeTextile> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<BarcodeTextile> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(BarcodeTextile entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends BarcodeTextile> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public <S extends BarcodeTextile> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends BarcodeTextile> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends BarcodeTextile> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends BarcodeTextile> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<BarcodeTextile> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BarcodeTextile> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BarcodeTextile> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends BarcodeTextile> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public <S extends BarcodeTextile> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends BarcodeTextile> List<S> saveAllAndFlush(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllInBatch(Iterable<BarcodeTextile> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub

	}

	@Override
	public BarcodeTextile getOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BarcodeTextile getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends BarcodeTextile> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends BarcodeTextile> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

}
