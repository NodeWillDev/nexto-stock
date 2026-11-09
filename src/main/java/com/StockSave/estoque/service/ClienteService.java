package com.StockSave.estoque.service;

import com.StockSave.estoque.dto.CriarClienteRequest;
import com.StockSave.estoque.dto.EditarClienteRequest;
import com.StockSave.estoque.entity.Cliente;
import com.StockSave.estoque.repository.ClienteRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository repository;
    StringBuilder builder = new StringBuilder();

    public ClienteService(ClienteRepository repository){
        this.repository = repository;
    }

    public List<Cliente> listarTodos(){
        return repository.findAll();
    }

    public Cliente criar(CriarClienteRequest request){
        if (Strings.isBlank(request.nome())){
            builder.append("Favor, informar o nome do cliente.\n");
        }
        if (Strings.isBlank(request.telefone())){
            builder.append("Favor, informar o telefone do cliente.\n");
        }
        if (Strings.isBlank(request.email())){
            builder.append("Favor, informar o e-mail do cliente.\n");
        }
        if (Strings.isBlank(request.telefone())){
            builder.append("Favor, informar o endereço do cliente.\n");
        }
        var cliente = new Cliente();
        cliente.setNome(request.nome());
        cliente.setTelefone(request.telefone());
        cliente.setEmail(request.email());
        cliente.setEndereco(request.endereco());
        return repository.save(cliente);
    }

    public Optional<Cliente> obterPeloId(Long id){
        return repository.findById(id);
    }

    public Cliente editar(EditarClienteRequest request) {
        if (Strings.isBlank(request.nome())) {
            builder.append("Favor, informar o nome do cliente.\n");
        }
        if (Strings.isBlank(request.telefone())) {
            builder.append("Favor, informar o telefone do cliente.\n");
        }
        if (Strings.isBlank(request.email())){
            builder.append("Favor, informar o e-mail do cliente.\n");
        }
        if (Strings.isBlank(request.telefone())){
            builder.append("Favor, informar o endereço do cliente.\n");
        }
        var old = repository.findById(request.id()).orElseThrow();
        old.setNome(request.nome());
        old.setTelefone(request.telefone());
        old.setEmail(request.email());
        old.setEndereco(request.endereco());
        return repository.save(old);
    }

    public void deletarPeloId(Long id){
        repository.deleteById(id);
    }
}
