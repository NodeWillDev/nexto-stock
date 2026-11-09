package com.StockSave.estoque.dto;

import com.StockSave.estoque.entity.Cliente;
import com.StockSave.estoque.entity.Produto;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EditarVendaRequest(
        Long id,
        Long cliente,
        Long produto,
        int quantidade,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate data,
        BigDecimal desconto,
        BigDecimal total,
        boolean parcelado,
        int qtdParcelas,
        int qtdPagas,
        String statusPagamento,
        String statusEntrega
) {}