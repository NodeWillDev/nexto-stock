package com.StockSave.estoque.controller;

import com.StockSave.estoque.service.VendaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    private final VendaService service;

    public HomeController(VendaService service){
        this.service = service;
    }

    @GetMapping
    public String home(){
        return "index";
    }

}
