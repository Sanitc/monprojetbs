package com.tactfactory.monprojetsb.monprojetsb.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import com.tactfactory.monprojetsb.monprojetsb.MonprojetsbApplicationTests;
import com.tactfactory.monprojetsb.monprojetsb.entities.Product;
import com.tactfactory.monprojetsb.monprojetsb.entities.User;
import com.tactfactory.monprojetsb.monprojetsb.mocks.repositories.MockitoUserRepository;
import com.tactfactory.monprojetsb.monprojetsb.repository.ProductRepository;
import com.tactfactory.monprojetsb.monprojetsb.repository.UserRepository;



@ActiveProfiles("test")
@TestPropertySource(locations = { "classpath:applicationtest.properties" })
@SpringBootTest(classes = MonprojetsbApplicationTests.class)
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@MockBean
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
		userService.save(entity);
	    User createdUser = userRepository.findById(entity.getId()).get();
	    assertTrue(isValid(createdUser, entity));
	}
	
	/**
	 * Test que la mise à jour d’un user n’a pas altérer les données de l’objet sauvegardé
	 */
	@Test
	public void TestUpdateUser() {
	       
        userService.save(entity);
        User userCreate = userRepository.findById(entity.getId()).get();
        userCreate.setFirstname("Solo");
        userService.save(userCreate);
        User userUpdate = userRepository.findById(entity.getId()).get();
        assertTrue(isValid(userUpdate, userCreate));
        
		
	}
	
	/**
	 * Test qu’un user est récupéré avec les bonnes données
	 */
	@Test
	public void getOneUser() {
		userRepository.save(entity);
	    User user = userService.getUserById(entity.getId());
	    assertTrue(isValid(user, entity));
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
	
	  private boolean isValid(User user1, User user2) {
	      boolean isValid = false;
	      if (user1.getFirstname().equals(user2.getFirstname()) && user1.getLastname().equals(user2.getLastname())) {
	          isValid = true;
	      }
	      return isValid;
	  }
	
}
