package com.StockSave.estoque.repository;

import com.StockSave.estoque.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}
