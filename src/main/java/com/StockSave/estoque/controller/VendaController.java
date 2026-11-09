package com.StockSave.estoque.controller;

import com.StockSave.estoque.dto.CriarVendaRequest;
import com.StockSave.estoque.dto.EditarVendaRequest;
import com.StockSave.estoque.repository.ClienteRepository;
import com.StockSave.estoque.repository.ProdutoRepository;
import com.StockSave.estoque.repository.VendaRepository;
import com.StockSave.estoque.service.VendaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/venda")
public class VendaController {
    private final VendaService service;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public VendaController(VendaService service, VendaRepository repository, ClienteRepository clienteRepository, ProdutoRepository produtoRepository){
        this.service = service;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    @GetMapping
    public ModelAndView index(){
        var mv = new ModelAndView("/venda/listagem_de_vendas");
        var lista = service.listarTodos();
        mv.addObject("vendas", lista);
        return mv;
    }

    @GetMapping("/criar")
    public ModelAndView criar() {
        var mv = new ModelAndView("venda/cadastro_venda");
        mv.addObject("clientes", clienteRepository.findAll());
        mv.addObject("produtos", produtoRepository.findAll());
        mv.addObject("criarVendaRequest", new CriarVendaRequest(null, null, null,1, null, null,null, false, 0, 0, null, null));
        return mv;
    }

    @PostMapping("/criar")
    public ModelAndView criar(@ModelAttribute CriarVendaRequest request) {
        ModelAndView mv;
        try {
            var venda = service.criar(request);
            return new ModelAndView("redirect:/venda");
        } catch (Exception e) {
            mv = new ModelAndView("venda/cadastro_venda");
            mv.addObject("criarVendaRequest", request);
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @GetMapping("/{id}")
    public ModelAndView vizualizar(@PathVariable("id") Long id) {
        var optional = service.obterPeloId(id);
        var mv = new ModelAndView("venda/visualizar_venda");
        mv.addObject("venda", optional.get());
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id) {
        var optional = service.obterPeloId(id);
        var mv = new ModelAndView("venda/editar_venda");
        var venda = optional.get();
        var request = new EditarVendaRequest(venda.getId(), venda.getCliente().getId(), venda.getProduto().getId(), venda.getQuantidade(), venda.getData(), venda.getDesconto(), venda.getTotal(), venda.isParcelado(), venda.getQtdParcelas(), venda.getQtdPagas(), venda.getStatusPagamento(), venda.getStatusEntrega());
        mv.addObject("clientes", clienteRepository.findAll());
        mv.addObject("produtos", produtoRepository.findAll());
        mv.addObject("editarVendaRequest", request);
        return mv;
    }

    @PostMapping("/editar")
    public ModelAndView editar(@ModelAttribute EditarVendaRequest request) {
        ModelAndView mv;
        try {
            var venda = service.editar(request);
            return new ModelAndView("redirect:/venda");
        } catch (Exception e) {
            mv = new ModelAndView("venda/editar_venda");
            mv.addObject("editarVendaRequest", request);
            mv.addObject("erro", e.getMessage());
            return mv;
        }
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable("id") Long id){
        service.deletarPeloId(id);
        return "redirect:/venda";
    }
}
