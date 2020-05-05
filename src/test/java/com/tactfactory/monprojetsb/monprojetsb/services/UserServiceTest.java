package com.tactfactory.monprojetsb.monprojetsb.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.tactfactory.monprojetsb.monprojetsb.entities.Product;
import com.tactfactory.monprojetsb.monprojetsb.entities.User;
import com.tactfactory.monprojetsb.monprojetsb.repository.UserRepository;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@EntityScan(basePackages="com.tactfactory.monprojetsb.monprojetsb")
@ComponentScan(basePackages="com.tactfactory.monprojetsb.monprojetsb")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Test que l’insertion d’un user ajoute bien un enregistrement dans la base de données
	 */
	@Test
	public void TestInsert() {
		Long b = userRepository.count();
		userService.save(new User());
		Long a = userRepository.count();
		
		assertEquals(b + 1, a);
	}
	
	/**
	 * • Test que l’insertion d’un user n’a pas altérer les données de l’objet sauvegardé
	 */
	@Test
	public void TestInsertUser() {
		User newUser = new User(4,"Kenobi","Ben", new ArrayList<Product>());
        Long id = userService.save(newUser).getId();
        User user = userRepository.getOne(id);
        assertEquals(user.getFirstname(), newUser.getFirstname());
        assertEquals(user.getLastname(), newUser.getLastname());
        assertEquals(user.getProducts(), newUser.getProducts());
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
	
	
}
