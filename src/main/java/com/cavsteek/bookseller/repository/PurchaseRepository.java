package com.cavsteek.bookseller.repository;

import com.cavsteek.bookseller.model.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<PurchaseHistory, Long>
{

}
