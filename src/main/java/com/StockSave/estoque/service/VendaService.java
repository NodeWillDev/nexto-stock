package com.StockSave.estoque.service;

import com.StockSave.estoque.dto.CriarVendaRequest;
import com.StockSave.estoque.dto.EditarVendaRequest;
import com.StockSave.estoque.entity.Venda;
import com.StockSave.estoque.repository.ClienteRepository;
import com.StockSave.estoque.repository.ProdutoRepository;
import com.StockSave.estoque.repository.VendaRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class VendaService {
    private final VendaRepository repository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;

    public VendaService(VendaRepository repository, ProdutoRepository produtoRepository, ClienteRepository clienteRepository){
        this.repository = repository;
        this.produtoRepository = produtoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Venda> listarTodos(){
        return repository.findAll();
    }

    public Venda criar(CriarVendaRequest request){
        StringBuilder builder = new StringBuilder();

        if (request.cliente() == null){
            builder.append("Favor, selecionar o cliente da venda.\n");
        }
        if (request.produto() == null){
            builder.append("Favor, selecionar o produto da venda.\n");
        }
        if (request.data() == null){
            builder.append("Favor, informar a data da venda.\n");
        }
        if (Strings.isBlank(request.statusPagamento())){
            builder.append("Favor, informar o status do pagamento.\n");
        }
        if (Strings.isBlank(request.statusEntrega())){
            builder.append("Favor, informar o status da entrega.\n");
        }
        if (builder.length() > 0)
            throw new RuntimeException(builder.toString());

        int quantidade = request.quantidade() <= 0 ? 1 : request.quantidade();

        var cliente = clienteRepository.findById(request.cliente()).orElseThrow();
        var produto = produtoRepository.findById(request.produto()).orElseThrow();

        if (produto.getQuantidade() < quantidade) {
            throw new RuntimeException(
                    "Quantidade indisponível em estoque. Disponível: " + produto.getQuantidade()
            );
        }

        BigDecimal desconto  = request.desconto() != null ? request.desconto() : BigDecimal.ZERO;
        BigDecimal total = produto.getValor()
                .multiply(BigDecimal.valueOf(quantidade))
                .subtract(desconto );

        var venda = new Venda();

        venda.setCliente(cliente);
        venda.setProduto(produto);
        venda.setQuantidade(quantidade);
        venda.setData(request.data());
        venda.setDesconto(desconto);
        venda.setTotal(total);
        venda.setParcelado(request.parcelado());
        venda.setQtdParcelas(request.qtdParcelas());
        venda.setQtdPagas(request.qtdPagas());
        venda.setStatusPagamento(request.statusPagamento());
        venda.setStatusEntrega(request.statusEntrega());

        if ("Entregue".equals(request.statusEntrega())) {
            produto.setQuantidade(produto.getQuantidade() - quantidade);
            produtoRepository.save(produto);
        }

        return repository.save(venda);
    }

    public Optional<Venda> obterPeloId(Long id){
        return repository.findById(id);
    }

    public Venda editar(EditarVendaRequest request){
        StringBuilder builder = new StringBuilder();

        if (request.cliente() == null){
            builder.append("Favor, selecionar o cliente da venda.\n");
        }
        if (request.produto() == null){
            builder.append("Favor, selecionar o produto da venda.\n");
        }
        if (request.data() == null){
            builder.append("Favor, informar a data da venda.\n");
        }
        if (Strings.isBlank(request.statusPagamento())){
            builder.append("Favor, informar o status do pagamento.\n");
        }
        if (Strings.isBlank(request.statusEntrega())){
            builder.append("Favor, informar o status da entrega.\n");
        }
        if (builder.length() > 0)
            throw new RuntimeException(builder.toString());

        int quantidade = request.quantidade() <= 0 ? 1 : request.quantidade();

        var old = repository.findById(request.id()).orElseThrow();
        var cliente = clienteRepository.findById(request.cliente()).orElseThrow();
        var produto = produtoRepository.findById(request.produto()).orElseThrow();

        boolean eraEntregue   = "Entregue".equals(old.getStatusEntrega());
        boolean agoraEntregue = "Entregue".equals(request.statusEntrega());

        int estoqueDisponivel = produto.getQuantidade() + (eraEntregue ? old.getQuantidade() : 0);

        if (estoqueDisponivel < quantidade) {
            throw new RuntimeException(
                    "Quantidade indisponível em estoque. Disponível: " + estoqueDisponivel
            );
        }

        if (!eraEntregue && agoraEntregue) {
            produto.setQuantidade(produto.getQuantidade() - quantidade);
            produtoRepository.save(produto);
        } else if (eraEntregue && !agoraEntregue) {
            produto.setQuantidade(produto.getQuantidade() + old.getQuantidade());
            produtoRepository.save(produto);
        } else if (eraEntregue && agoraEntregue && old.getQuantidade() != quantidade) {
            produto.setQuantidade(produto.getQuantidade() + old.getQuantidade() - quantidade);
            produtoRepository.save(produto);
        }

        String statusPagamento = request.statusPagamento();
        if (request.parcelado() && request.qtdParcelas() > 0 && request.qtdParcelas() == request.qtdPagas()) {
            statusPagamento = "Pago";
        }

        BigDecimal desconto = request.desconto() != null ? request.desconto() : BigDecimal.ZERO;
        BigDecimal total = produto.getValor()
                .multiply(BigDecimal.valueOf(quantidade))
                .subtract(desconto);


        old.setCliente(cliente);
        old.setProduto(produto);
        old.setQuantidade(quantidade);
        old.setData(request.data());
        old.setDesconto(desconto);
        old.setTotal(total);
        old.setParcelado(request.parcelado());
        old.setQtdParcelas(request.qtdParcelas());
        old.setQtdPagas(request.qtdPagas());
        old.setStatusPagamento(statusPagamento);
        old.setStatusEntrega(request.statusEntrega());

        return repository.save(old);
    }

    public void deletarPeloId(Long id){
        repository.deleteById(id);
    }
}
