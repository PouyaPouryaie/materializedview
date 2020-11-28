package ir.bigz.microservice.materializedview.repository;

import ir.bigz.microservice.materializedview.entity.PurchaseOrderSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderSummaryRepository extends JpaRepository<PurchaseOrderSummary, String> {
}
