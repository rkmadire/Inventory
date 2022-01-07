package com.otsi.retail.inventory.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnRepo {
	
	/*
	 * @Query("select column_name from information_schema.columns where table_name In ('barcode_textile','product_textile')"
	 * ) List<String> findAllColumnNames();
	 */
}
