package com.tts.dashboard.controllers;

import com.tts.dashboard.entities.Product;
import com.tts.dashboard.services.SupplierService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SupplierHTMLController {
    private SupplierService service;

    public SupplierHTMLController(SupplierService service) {
        this.service = service;
    }

    @GetMapping("/serviceproducts")
    public String findProductsFromService(@RequestParam(required = false,
            defaultValue = "1") int id, Model model) {
        List<Product> products = service.getProductsFromService(id);
        model.addAttribute("products", products);
        return "products";
    }
}
