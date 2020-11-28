package ir.bigz.microservice.materializedview.service.impl;

import ir.bigz.microservice.materializedview.dto.PurchaseOrderSummaryDto;
import ir.bigz.microservice.materializedview.entity.Product;
import ir.bigz.microservice.materializedview.entity.PurchaseOrder;
import ir.bigz.microservice.materializedview.entity.User;
import ir.bigz.microservice.materializedview.repository.ProductRepository;
import ir.bigz.microservice.materializedview.repository.PurchaseOrderRepository;
import ir.bigz.microservice.materializedview.repository.PurchaseOrderSummaryRepository;
import ir.bigz.microservice.materializedview.repository.UserRepository;
import ir.bigz.microservice.materializedview.service.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderSummaryRepository purchaseOrderSummaryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    private List<User> users;
    private List<Product> products;

    @PostConstruct
    private void init(){
        this.users = this.userRepository.findAll();
        this.products = this.productRepository.findAll();
    }

    @Override
    public void placeOrder(int userIndex, int productIndex) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setProductId(this.products.get(productIndex - 1).getId());
        purchaseOrder.setUserId(this.users.get(userIndex - 1).getId());
        purchaseOrder.setOrderDate(new Date());
        log.info("purchase order is " + purchaseOrder.toString());
        this.purchaseOrderRepository.save(purchaseOrder);
    }

    @Override
    public List<PurchaseOrderSummaryDto> getSaleSummary() {
        return this.purchaseOrderSummaryRepository.findAll()
                .stream()
                .map(pos -> {
                    PurchaseOrderSummaryDto dto = new PurchaseOrderSummaryDto();
                    dto.setState(pos.getState());
                    dto.setTotalSale(pos.getTotalSale());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
