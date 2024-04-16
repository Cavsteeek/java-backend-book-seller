package com.cavsteek.bookseller.repository;

import com.cavsteek.bookseller.model.PurchaseHistory;
import com.cavsteek.bookseller.repository.projection.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long>
{

}