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

	/**
	 * Test que l�insertion d�un produit ajoute bien un enregistrement dans la base de donn�es
	 */
	@Test
	public void TestInsert() {
		Long b = productRepository.count();
		productService.save(new Product());
		Long a = productRepository.count();
		
		assertEquals(b + 1, a);
	}
	
	/**
	 * � Test que l�insertion d�un produit n�a pas alt�rer les donn�es de l�objet sauvegard�
	 */
	@Test
	public void TestInsertProduct() {
		Product newProduct = new Product(null, "awing", (float) 10);
        Long id = productService.save(newProduct).getId();
        Product productBDD = productRepository.getProductById(id);

        assertTrue(compare(newProduct, productBDD));
	}
	
	/**
	 * Test que la mise � jour d�un produit n�a pas alt�rer les donn�es de l�objet sauvegard�
	 */
	@Test
	public void TestUpdateProduct() {
		 
        Product product = new Product(null, "awing", (float) 10);
        Long id = productService.save(product).getId();

        Product productBDD = productRepository.getProductById(id);
        productBDD.setPrice((float) 20);

        Long idUpdate = productService.save(productBDD).getId();
        Product productUpdated = productRepository.getProductById(id);

        assertTrue(compare(productBDD, productUpdated));
	}
	
	/**
	 * Test qu�un produit est r�cup�r� avec les bonnes donn�es
	 */
	@Test
	public void getOneProduct() {
		Product product = new Product(null, "awing", (float) 10);
		Long id = productRepository.save(product).getId();
		Product productBDD = productService.getProductById(id);

		assertTrue(compare(product, productBDD));
	}
	
	/**
	 * Test qu�une liste de produit est r�cup�r� avec les bonnes donn�es
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
	 * Test que la suppression d�un produit d�cr�mente le nombre d�enregistrement pr�sent
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
	 * Test que la suppression d�un produit supprime bien le bon �l�ment
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
}
