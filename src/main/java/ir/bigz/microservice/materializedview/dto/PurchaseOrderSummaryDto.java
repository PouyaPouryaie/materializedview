package ir.bigz.microservice.materializedview.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseOrderSummaryDto {

    private String state;
    private double totalSale;
}
