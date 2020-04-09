package com.tactfactory.monprojetsb.monprojetsb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tactfactory.monprojetsb.monprojetsb.entities.Product;
import com.tactfactory.monprojetsb.monprojetsb.repository.ProductRepository;


@Controller
@RequestMapping(value = "/product")
public class ProductController {
	
	@Autowired
    private ProductRepository repository;

    public ProductController(ProductRepository productRepository) {
        this.repository = productRepository;
    }

    @RequestMapping(value = { "/index", "/" })
    public String index(Model model) {
        model.addAttribute("page", "Product index");
        model.addAttribute("items", repository.findAll());
        return "product/index";
    }

    @GetMapping(value = {"/create"})
    public String createGet(Model model) {
        model.addAttribute("page", "Product create");
        return "product/create";
    }

    @PostMapping(value = {"/create"})
    public String createPost(@ModelAttribute Product product) {
        if (product != null) {
            repository.save(product);
        }
        return "redirect:index";
    }

    @GetMapping(value = {"/delete/{id}"})
    public String delete(@PathVariable(value = "id") long id) {
//        Product product = details(id);
//        repository.delete(product);
        return "redirect:index";
    }

    @GetMapping(value = {"/details/{id}"})
    public String details(@PathVariable(value = "id") long id) {
//        return repository.getOne(id);
        return "redirect:index";
    }
	
}
