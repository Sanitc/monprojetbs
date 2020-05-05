package com.tactfactory.monprojetsb.monprojetsb.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

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
	
	public void TestInsertUser() {
		Long b = userRepository.count();
		userService.save(new User());
		Long a = userRepository.count();
		
		assertEquals(b + 1, a);
	}
	
	
}
