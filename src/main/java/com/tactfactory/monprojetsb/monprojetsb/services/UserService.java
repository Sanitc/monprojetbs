package com.tactfactory.monprojetsb.monprojetsb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tactfactory.monprojetsb.monprojetsb.entities.User;
import com.tactfactory.monprojetsb.monprojetsb.repository.UserRepository;



@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	public User save(User item) {
        return this.userRepository.save(item);
    }

    public void delete(User item) {
        this.userRepository.delete(item);
    }

    public User getOne(Long id) {
        return this.userRepository.getOne(id);
    }
    
    public User getUserById(Long id) {
        return this.userRepository.getUserById(id);
    }
    
    public void saveList(List<User> users) {
        for (User user : users) {
            user.setId(userRepository.save(user).getId());
        }
    }
    
    public List<User> findAll() {
        return this.userRepository.findAll();
    }
}
