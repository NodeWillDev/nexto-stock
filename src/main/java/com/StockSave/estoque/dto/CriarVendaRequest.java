package com.StockSave.estoque.dto;

import com.StockSave.estoque.entity.Cliente;
import com.StockSave.estoque.entity.Produto;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CriarVendaRequest(Long id, Long cliente, Long produto, int quantidade, LocalDate data, BigDecimal desconto, BigDecimal total, boolean parcelado, int qtdParcelas, int qtdPagas, String statusPagamento, String statusEntrega) {
}