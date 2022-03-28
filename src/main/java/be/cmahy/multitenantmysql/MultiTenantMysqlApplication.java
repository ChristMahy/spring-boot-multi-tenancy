package be.cmahy.multitenantmysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"be.cmahy.multitenantmysql"})
public class MultiTenantMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiTenantMysqlApplication.class, args);
	}

}
