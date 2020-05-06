package com.tactfactory.monprojetsb.monprojetsb.mocks.repositories;

import java.util.Arrays;
import java.util.Optional;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.tactfactory.monprojetsb.monprojetsb.entities.Product;
import com.tactfactory.monprojetsb.monprojetsb.entities.User;
import com.tactfactory.monprojetsb.monprojetsb.repository.ProductRepository;

public class MockitoProductRepository {
	protected final ProductRepository repository;

	  public Product entity;

	  public Product resultEntity;
	  
	  public Optional<Product> resultOptional;
	  
	    public MockitoProductRepository(ProductRepository repository) {
	        this.repository = repository;

	        this.entity = new Product();
	        this.entity.setName("p1");
	        this.entity.setPrice(8.0F);
	    }
	    
		  public void intialize() {
			    // this.configure();

				this.resultEntity = new Product();
			    this.resultEntity.setId(this.entity.getId());
			    this.resultEntity.setName(this.entity.getName());
			    this.resultEntity.setPrice(this.entity.getPrice());

			    this.resultEntity.setId(1L);
			    this.resultOptional = Optional.of(this.resultEntity);

			    Mockito.when(this.repository.findById(1L)).thenReturn(this.resultOptional);

			    Mockito.when(this.repository.findAll((Pageable) ArgumentMatchers.any()))
			        .thenReturn(new PageImpl<>(Arrays.asList(this.resultEntity)));

			    Mockito.when(this.repository.save(ArgumentMatchers.any())).thenAnswer(new Answer<Product>() {

			      @Override
			      public Product answer(InvocationOnMock invocation) throws Throwable {
			        Product product = invocation.getArgument(0);
			        product.setId(1L);
			        return MockitoProductRepository.this.resultEntity;
			      }
			    });
			  }
}
