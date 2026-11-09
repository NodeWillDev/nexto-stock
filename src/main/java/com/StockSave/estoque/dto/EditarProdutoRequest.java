package com.StockSave.estoque.dto;

import java.math.BigDecimal;

public record EditarProdutoRequest(Long id, String sku, String modelo, String time, String tamanho, BigDecimal valor, int quantidade) {
}
