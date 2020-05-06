package com.tactfactory.monprojetsb.monprojetsb.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import com.tactfactory.monprojetsb.monprojetsb.entities.Product;
import com.tactfactory.monprojetsb.monprojetsb.entities.User;
import com.tactfactory.monprojetsb.monprojetsb.repository.ProductRepository;
import com.tactfactory.monprojetsb.monprojetsb.MonprojetsbApplicationTests;


@ActiveProfiles("test")
@TestPropertySource(locations = { "classpath:applicationtest.properties" })
@SpringBootTest(classes = MonprojetsbApplicationTests.class)
public class ProductServiceTest {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;

	private Product entity;
	
	/**
	 * Test que l’insertion d’un produit ajoute bien un enregistrement dans la base de données
	 */
	@Test
	public void TestInsert() {
		Product product = new Product();
		productService.save(product);
        Long b = productRepository.count();
        productService.delete(product);
        Long a = productRepository.count();

        assertEquals(b - 1, a);
	}
	
	/**
	 * • Test que l’insertion d’un produit n’a pas altérer les données de l’objet sauvegardé
	 */
	@Test
	public void TestInsertProduct() {
		productService.save(entity);
	    Product product = productRepository.findById(entity.getId()).get();
	    assertTrue(isValid(product, entity));
	}
	
	/**
	 * Test que la mise à jour d’un produit n’a pas altérer les données de l’objet sauvegardé
	 */
	@Test
	public void TestUpdateProduct() {
		 
		productService.save(entity);
	    Product product = productRepository.findById(entity.getId()).get();
	    product.setName("NouveauNom");
	    productService.save(product);
	    Product productUpdate = productRepository.findById(entity.getId()).get();
	    assertTrue(isValid(productUpdate, product));
	}
	
	/**
	 * Test qu’un produit est récupéré avec les bonnes données
	 */
	@Test
	public void getOneProduct() {
		 productRepository.save(entity);
	     Product product = productService.getProductById(entity.getId());
	     assertTrue(isValid(product, entity));
	}
	
	/**
	 * Test qu’une liste de produit est récupéré avec les bonnes données
	 */
	public void TestListProduct() {
	    List<Product> products = new ArrayList<Product>();
        Product product1 = new Product(null, "awing", (float) 10);
        products.add(product1);
        Product product2 = new Product(null, "bwing", (float) 10);
        products.add(product2);

        productService.saveList(products);

        List<Product> productsFetch = productService.findAll();

        for (int i = 0; i < productsFetch.size(); i++) {
            assertTrue(compare(products.get(i), productsFetch.get(i)));
        }
	}

	/**
	 * Test que la suppression d’un produit décrémente le nombre d’enregistrement présent
	 */
	@Test
	public void TestDelete() {
		 Product productTemp = new Product();
	        productService.save(productTemp);
	        Long before = productRepository.count();
	        productService.delete(productTemp);
	        Long after = productRepository.count();

	        assertEquals(before - 1, after);
	}
	
	/**
	 * Test que la suppression d’un produit supprime bien le bon élément
	 */
	@Test
	public void TestDeleteProduct() {
		Product product = new Product(null, "awing", (float) 10);
        Long id = productRepository.save(product).getId();
        productService.delete(product);

        Product deleteProduct = productService.getProductById(id);
        assertNull(deleteProduct);
	}
	
	 public Boolean compare(Product product1, Product product2) {
	        boolean result = true;

	        if (!product1.getId().equals(product2.getId())) {
	            result = false;
	            System.out.println("id: " + product1.getId() + " " + product2.getId());
	        }
	        if (!product1.getName().equals(product2.getName())) {
	            result = false;
	            System.out.println("name: " + product1.getName() + " " + product2.getName());
	        }
	        if (!product1.getPrice().equals(product2.getPrice())) {
	            result = false;
	            System.out.println("price: " + product1.getPrice() + " " + product2.getPrice());
	        }

	        return result;
	    }
	 
	 private boolean isValid(Product product1, Product product2) {
	        boolean isValid = false;
	        if (product1.getName().equals(product2.getName()) && product1.getPrice().equals(product2.getPrice())) {
	            isValid = true;
	        }
	        return isValid;
	    }
}
