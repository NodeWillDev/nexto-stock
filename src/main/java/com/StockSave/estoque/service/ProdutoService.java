package com.StockSave.estoque.service;

import com.StockSave.estoque.dto.CriarProdutoRequest;
import com.StockSave.estoque.dto.EditarProdutoRequest;
import com.StockSave.estoque.entity.Produto;
import com.StockSave.estoque.repository.ProdutoRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository repository;
    StringBuilder builder = new StringBuilder();


    public ProdutoService(ProdutoRepository repository){
        this.repository = repository;
    }

    public List<Produto> listarTodos(){
        return repository.findAll();
    }

    public Produto criar(CriarProdutoRequest request){
        if (Strings.isBlank(request.sku())){
            builder.append("Favor, informar o SKU do produto.\n");
        }
        if (Strings.isBlank(request.modelo())){
            builder.append("Favor, informar o modelo do produto.\n");
        }
        if (Strings.isBlank(request.time())){
            builder.append("Favor, informar o time do produto.\n");
        }
        if (Strings.isBlank(request.tamanho())){
            builder.append("Favor, informar o tamanho do produto.\n");
        }
        if (Strings.isBlank(request.valor())){
            builder.append("Favor, informar o valor do produto.\n");
        }
        if (request.quantidade() <= 0){
            builder.append("Favor, informar a quantidade do produto.\n");
        }
        BigDecimal valor = new BigDecimal(request.valor()
                        .replace("R$", "")
                        .replace(".", "")
                        .replace(",", ".")
                        .trim()
        );
        var produto = new Produto();
        produto.setSku(request.sku());
        produto.setModelo(request.modelo());
        produto.setTime(request.time());
        produto.setTamanho(request.tamanho());
        produto.setValor(valor);
        produto.setQuantidade(request.quantidade());
        return repository.save(produto);
    }

    public Optional<Produto> obterPeloId(Long id){
        return repository.findById(id);
    }

    public Produto editar(EditarProdutoRequest request){
        if (Strings.isBlank(request.sku())){
            builder.append("Favor, informar o SKU do produto.\n");
        }
        if (Strings.isBlank(request.modelo())){
            builder.append("Favor, informar o modelo do produto.\n");
        }
        if (Strings.isBlank(request.time())){
            builder.append("Favor, informar o time do produto.\n");
        }
        if (Strings.isBlank(request.tamanho())){
            builder.append("Favor, informar o tamanho do produto.\n");
        }
        if (Strings.isBlank(String.valueOf(request.valor()))){
            builder.append("Favor, informar o valor do produto.\n");
        }
        if (request.quantidade() <= 0){
            builder.append("Favor, informar a quantidade do produto.\n");
        }
        var old = repository.findById(request.id()).orElseThrow();
        old.setSku(request.sku());
        old.setModelo(request.modelo());
        old.setTime(request.time());
        old.setTamanho(request.tamanho());
        old.setValor(request.valor());
        old.setQuantidade(request.quantidade());
        return repository.save(old);
    }

    public void deletarPeloId(Long id){
        repository.deleteById(id);
    }
}
