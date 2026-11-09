package com.StockSave.estoque.dto;

public record EditarClienteRequest(Long id, String nome, String telefone, String email, String endereco) {
}
