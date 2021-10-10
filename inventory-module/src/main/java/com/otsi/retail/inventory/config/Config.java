package com.otsi.retail.inventory.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Config {

	@Value("${getCatagories_url}")
	private String getCatagoriesUrl;

}
