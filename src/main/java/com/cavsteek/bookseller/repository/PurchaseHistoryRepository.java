package com.cavsteek.bookseller.repository;

import com.cavsteek.bookseller.model.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long>
{

}