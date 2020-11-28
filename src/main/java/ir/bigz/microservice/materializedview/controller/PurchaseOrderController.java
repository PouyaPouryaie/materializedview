package ir.bigz.microservice.materializedview.controller;

import ir.bigz.microservice.materializedview.dto.PurchaseOrderSummaryDto;
import ir.bigz.microservice.materializedview.service.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("po")
@Slf4j
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @GetMapping("/sale/{userIndex}/{prodIndex}")
    public void placeOrder(@PathVariable final int userIndex,
                           @PathVariable final int prodIndex){
        log.info("data Request is " + userIndex + " " + prodIndex);
        this.purchaseOrderService.placeOrder(userIndex, prodIndex);
    }

    @GetMapping("/summary")
    public List<PurchaseOrderSummaryDto> getSummary(){
        return this.purchaseOrderService.getSaleSummary();
    }
}
