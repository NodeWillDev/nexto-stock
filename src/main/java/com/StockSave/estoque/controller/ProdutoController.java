package com.StockSave.estoque.controller;

import com.StockSave.estoque.dto.CriarProdutoRequest;
import com.StockSave.estoque.dto.EditarProdutoRequest;
import com.StockSave.estoque.service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/produto")
public class ProdutoController {
    private final ProdutoService service;

    public ProdutoController(ProdutoService service){
        this.service = service;
    }

    @GetMapping
    public ModelAndView index(){
        var mv = new ModelAndView("/produto/listagem_de_produtos");
        var lista = service.listarTodos();
        mv.addObject("produtos", lista);
        return mv;
    }

    @GetMapping("/criar")
    public ModelAndView criar(){
        var mv = new ModelAndView("/produto/cadastro_produto");
        mv.addObject("criarProdutoRequest", new CriarProdutoRequest(null, null, null, null, null, null, 0));
        return mv;
    }

    @PostMapping("/criar")
    public ModelAndView criar(@ModelAttribute CriarProdutoRequest request){
        ModelAndView mv;
        try {
            var produto = service.criar(request);
            return new ModelAndView("redirect:/produto");
        } catch (Exception e) {
            mv = new ModelAndView("produto/cadastro_produto");
            mv.addObject("criarProdutoRequest", request);
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @GetMapping("/{id}")
    public ModelAndView visualizar(@PathVariable("id") Long id){
        var optional = service.obterPeloId(id);
        var mv = new ModelAndView("/produto/visualizar_produto");
        mv.addObject("produto", optional.get());
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        var optional = service.obterPeloId(id);
        var mv = new ModelAndView("produto/editar_produto");
        var produto = optional.get();
        var request = new EditarProdutoRequest(produto.getId(), produto.getSku(), produto.getModelo(), produto.getTime(), produto.getTamanho(), produto.getValor(), produto.getQuantidade());
        mv.addObject("editarProdutoRequest", request);
        return mv;
    }

    @PostMapping("/editar")
    public ModelAndView editar(@ModelAttribute EditarProdutoRequest request) {
        ModelAndView mv;
        try {
            var produto = service.editar(request);
            return new ModelAndView("redirect:/produto");
        } catch (Exception e) {
            mv = new ModelAndView("produto/editar_produto");
            mv.addObject("editarProdutoRequest", request);
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable("id") Long id){
        service.deletarPeloId(id);
        return "redirect:/produto";
    }


}
