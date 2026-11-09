package com.StockSave.estoque.dto;

public record CriarClienteRequest (Long id, String nome, String telefone, String email, String endereco) {
}
