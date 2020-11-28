package ir.bigz.microservice.materializedview.service.data;

import com.github.javafaker.Faker;
import ir.bigz.microservice.materializedview.entity.Product;
import ir.bigz.microservice.materializedview.entity.PurchaseOrder;
import ir.bigz.microservice.materializedview.entity.User;
import ir.bigz.microservice.materializedview.repository.ProductRepository;
import ir.bigz.microservice.materializedview.repository.PurchaseOrderRepository;
import ir.bigz.microservice.materializedview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@ConditionalOnProperty(name = "app.generator.enabled", havingValue = "true")
public class DataGenerator implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Override
    public void run(String... args) throws Exception {

        Faker faker = new Faker();

        createUser(faker);
        createProduct(faker);
//        IntStream.range(0, 10)
//                .forEach(i -> createOrder(faker));
        createOrder(faker);
    }

    private void createOrder(Faker faker){

        List<Product> products = this.productRepository.findAll();
        List<User> users = this.userRepository.findAll();

        List<PurchaseOrder> purchaseOrderList = IntStream.range(0, 100_000).mapToObj(i -> {
            int userIndex = faker.number().numberBetween(0, 10000);
            int prodIndex = faker.number().numberBetween(0, 1000);
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setUserId(users.get(userIndex).getId());
            purchaseOrder.setProductId(products.get(prodIndex).getId());
            return purchaseOrder;
        }).collect(Collectors.toList());
        purchaseOrderRepository.saveAll(purchaseOrderList);
    }

    private void createUser(Faker faker){
        List<User> userList = IntStream.range(0, 10000)
                .mapToObj(i -> {
                    User user = new User();
                    user.setFirstName(faker.name().firstName());
                    user.setLastName(faker.name().lastName());
                    user.setState(faker.address().stateAbbr());
                    return user;
                }).collect(Collectors.toList());
        userRepository.saveAll(userList);
    }

    private void createProduct(Faker faker){
        List<Product> productList = IntStream.range(0, 1000)
                .mapToObj(i -> {
                    Product product = new Product();
                    product.setDescription(faker.book().title());
                    product.setPrice(faker.number().numberBetween(1, 200));
                    return product;
                }).collect(Collectors.toList());

        productRepository.saveAll(productList);
    }
}
