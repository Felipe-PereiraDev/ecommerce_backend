package br.com.felipedev.ecommerce.repository;

import br.com.felipedev.ecommerce.model.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
}
