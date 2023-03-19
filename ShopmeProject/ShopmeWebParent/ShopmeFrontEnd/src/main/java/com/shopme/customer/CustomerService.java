package com.shopme.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;
import com.shopme.setting.CountryRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CountryRepository countryRepo; // quốc gia
	
	@Autowired
	private CustomerRepository customerRepo; // khách hàng
	
	// lấy tất cả quốc gia
	public List<Country> listAllCountry() {
		return countryRepo.findAllByOrderByNameAsc();
	}
	
	public boolean isEmailUnique(String email) {
		Customer customer = customerRepo.findByEmail(email);
		return customer == null;
	}
	

}
