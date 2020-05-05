package com.tactfactory.monprojetsb.monprojetsb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tactfactory.monprojetsb.monprojetsb.entities.Product;
import com.tactfactory.monprojetsb.monprojetsb.repository.ProductRepository;


@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	 public Product save(Product item) {
	        return this.productRepository.save(item);
	    }

	    public void delete(Product item) {
	        this.productRepository.delete(item);
	    }

	    public Product getOne(Long id) {
	        return this.productRepository.getOne(id);
	    }
}
