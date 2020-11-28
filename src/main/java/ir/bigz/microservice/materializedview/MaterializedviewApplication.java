package ir.bigz.microservice.materializedview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MaterializedviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaterializedviewApplication.class, args);
	}

}
