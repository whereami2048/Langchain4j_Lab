package shiroi.stockengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class StockengineApplication {

	static void main(String[] args) {
		SpringApplication.run(StockengineApplication.class, args);
	}
}
