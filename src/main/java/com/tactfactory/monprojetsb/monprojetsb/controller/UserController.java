package com.tactfactory.monprojetsb.monprojetsb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tactfactory.monprojetsb.monprojetsb.entities.User;
import com.tactfactory.monprojetsb.monprojetsb.repository.UserRepository;

@Controller
@RequestMapping(value="user")
public class UserController {

	@Autowired
	private UserRepository repository;

    public UserController(UserRepository userRepository) {
        this.repository = userRepository;
    }
    @RequestMapping(value = { "/index", "/" })
    public String index(Model model) {
        model.addAttribute("page", "User index");
        model.addAttribute("items", repository.findAll());
        return "user/index";
    }
    
    @GetMapping(value = {"/create"})
    public String createGet(Model model) {
        model.addAttribute("page", "User create");
        return "user/create";
    }
    
    @PostMapping(value = {"/create"})
    public String createPost(@ModelAttribute User user) {
        if (user != null) {
            repository.save(user);
        }
        return "redirect:index";
    }
    
    @GetMapping(value = {"/delete/{id}"})
    public void delete(@PathVariable(value = "id") long id) {
        User user = details(id);
        repository.delete(user);
    }
    
    @GetMapping(value = {"/details/{id}"})
    public User details(@PathVariable(value = "id") long id) {
        return repository.getOne(id);
    }
    
}
