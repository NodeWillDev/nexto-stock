package com.StockSave.estoque.repository;

import com.StockSave.estoque.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
