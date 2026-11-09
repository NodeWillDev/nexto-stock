package com.StockSave.estoque.dto;

public record CriarProdutoRequest(Long id, String sku, String modelo, String time, String tamanho, String valor, int quantidade) {
}