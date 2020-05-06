package com.tactfactory.monprojetsb.monprojetsb.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertNull;


import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.tactfactory.monprojetsb.monprojetsb.MonprojetsbApplicationTests;
import com.tactfactory.monprojetsb.monprojetsb.entities.Product;
import com.tactfactory.monprojetsb.monprojetsb.entities.User;
import com.tactfactory.monprojetsb.monprojetsb.mocks.repositories.MockitoUserRepository;
import com.tactfactory.monprojetsb.monprojetsb.repository.ProductRepository;
import com.tactfactory.monprojetsb.monprojetsb.repository.UserRepository;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;



@ActiveProfiles("test")
@TestPropertySource(locations = { "classpath:applicationtest.properties" })
@SpringBootTest(classes = MonprojetsbApplicationTests.class)
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	private User entity;
	 
	@BeforeEach
	public void setUp() throws Exception {
		final MockitoUserRepository mock = new MockitoUserRepository(this.userRepository);
	    mock.intialize();
	    this.entity = mock.entity;
	}
	 
	
	/**
	 * Test que l’insertion d’un user ajoute bien un enregistrement dans la base de données
	 */
	@Test
	public void TestInsert() {
		Long b = userRepository.count();
		userService.save(this.entity);
		Long a = userRepository.count();
		
		assertEquals(b + 1, a);
	}
	
	/**
	 * • Test que l’insertion d’un user n’a pas altérer les données de l’objet sauvegardé
	 */
	@Test
	public void TestInsertUser() {
		User newUser = new User(null,"Kenobi","Ben", new ArrayList<Product>());
        Long id = userService.save(newUser).getId();
        User user = userRepository.getOne(id);
        assertEquals(user.getFirstname(), newUser.getFirstname());
        assertEquals(user.getLastname(), newUser.getLastname());
        assertEquals(user.getProducts(), newUser.getProducts());
	}
	
	/**
	 * Test que la mise à jour d’un user n’a pas altérer les données de l’objet sauvegardé
	 */
	@Test
	public void TestUpdateUser() {
	
		// Create User
        User userBase = new User(null,"Skywalker","Luke", new ArrayList<Product>());
        Long id = userService.save(userBase).getId();

        //Create Product 1
        Product newProduct1 = new Product(null, "xwing",(float) 10);
        Long p1 = productRepository.save(newProduct1).getId();
        Product product1 = productRepository.getProductById(p1);

        // Create product 2
        Product newProduct2 = new Product(null, "ywing",(float) 10);
        Long p2 = productRepository.save(newProduct2).getId();
        Product product2 = productRepository.getProductById(p2);

        // Add products 1 and 2 to products
        List<Product> products = new ArrayList<Product>();
        products.add(product1);
        products.add(product2);

        // Get user and set products
        User user = userRepository.getUserById(id);
        user.setProducts(products);

        // Update user and get id to check modifications
        Long idUpdate = userService.save(user).getId();
        User userUpdate = userRepository.getUserById(id);

        assertTrue(compare(user, userUpdate));
		
	}
	
	/**
	 * Test qu’un user est récupéré avec les bonnes données
	 */
	@Test
	public void getOneUser() {
		User newUser =  new User(null,"Skywalker","Luke", new ArrayList<Product>());
        Long id = userRepository.save(newUser).getId();
        User user = userService.getUserById(id);

        assertTrue(compare(newUser, user));
	}
	
	/**
	 * Test qu’une liste de user est récupéré avec les bonnes données
	 */
	public void TestListUser() {
		List<User> users = new ArrayList<User>();
        User user1 =  new User(null,"Skywalker","Luke", new ArrayList<Product>());
        users.add(user1);
        User user2 =  new User(null,"Skywalker","Leia", new ArrayList<Product>());
        users.add(user2);

        userService.saveList(users);

        List<User> usersFetch = userService.findAll();

        for (int i = 0; i < usersFetch.size(); i++) {
            assertTrue(compare(users.get(i), usersFetch.get(i)));
        }
	}

	/**
	 * Test que la suppression d’un user décrémente le nombre d’enregistrement présent
	 */
	@Test
	public void TestDelete() {
		User user = new User();
        userService.save(user);
        Long b = userRepository.count();
        userService.delete(user);
        Long a = userRepository.count();

        assertEquals(b - 1, a);
	}
	
	/**
	 * Test que la suppression d’un user supprime bien le bon élément
	 */
	@Test
	public void TestDeleteUser() {
		User user = new User(null,"Skywalker","Luke", new ArrayList<Product>());
        Long id = userRepository.save(user).getId();
        userService.delete(user);

        User userDelete = userService.getUserById(id);
        assertNull(userDelete);
	}
	
	public Boolean compare(User user1, User user2) {
        boolean result = true;

        if (!user1.getId().equals(user2.getId())) {
            result = false;
            System.out.println("id: " + user1.getId() + " " + user2.getId());
        }
        if (!user1.getFirstname().equals(user2.getFirstname())) {
            result = false;
            System.out.println("firstname: " + user1.getFirstname() + " " + user2.getFirstname());
        }
        if (!user1.getLastname().equals(user2.getLastname())) {
            result = false;
            System.out.println("lastname: " + user1.getLastname() + " " + user2.getLastname());
        }

        return result;
    }
	
}
