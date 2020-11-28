package ir.bigz.microservice.materializedview.service;

import ir.bigz.microservice.materializedview.dto.PurchaseOrderSummaryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PurchaseOrderService {

    void placeOrder(int userIndex, int productIndex);
    List<PurchaseOrderSummaryDto> getSaleSummary();
}
