package com.StockSave.estoque.controller;

import com.StockSave.estoque.dto.CriarClienteRequest;
import com.StockSave.estoque.dto.EditarClienteRequest;
import com.StockSave.estoque.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service){
        this.service = service;
    }

    @GetMapping
    public ModelAndView index(){
        var mv = new ModelAndView("/cliente/listagem_de_clientes");
        var lista = service.listarTodos();
        mv.addObject("clientes", lista);
        return mv;
    }

    @GetMapping("/criar")
    public ModelAndView criar() {
        var mv = new ModelAndView("cliente/cadastro_cliente");
        mv.addObject("criarClienteRequest", new CriarClienteRequest(null, null, null, null, null));
        return mv;
    }

    @PostMapping("/criar")
    public ModelAndView criar(@ModelAttribute CriarClienteRequest request) {
        ModelAndView mv;
        try {
            var cliente = service.criar(request);
            return new ModelAndView("redirect:/cliente");
        } catch (Exception e) {
            mv = new ModelAndView("cliente/cadastro_cliente");
            mv.addObject("criarClienteRequest", request);
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @GetMapping("/{id}")
    public ModelAndView vizualizar(@PathVariable("id") Long id) {
        var optional = service.obterPeloId(id);
        var mv = new ModelAndView("cliente/visualizar_cliente");
        mv.addObject("cliente", optional.get());
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        var optional = service.obterPeloId(id);
        var mv = new ModelAndView("cliente/editar_cliente");
        var cliente = optional.get();
        var request = new EditarClienteRequest(cliente.getId(), cliente.getNome(),cliente.getTelefone(), cliente.getEmail(), cliente.getEndereco());
        mv.addObject("editarClienteRequest", request);
        return mv;
    }

    @PostMapping("/editar")
    public ModelAndView editar(@ModelAttribute EditarClienteRequest request) {
        ModelAndView mv;
        try {
            var cliente = service.editar(request);
            return new ModelAndView("redirect:/cliente");
        } catch (Exception e) {
            mv = new ModelAndView("cliente/editar_cliente");
            mv.addObject("editarClienteRequest", request);
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable("id") Long id){
        service.deletarPeloId(id);
        return "redirect:/cliente";
    }
}
