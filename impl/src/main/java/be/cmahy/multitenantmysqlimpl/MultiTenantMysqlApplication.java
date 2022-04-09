package be.cmahy.multitenantmysqlimpl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"be.cmahy"}, exclude = { DataSourceAutoConfiguration.class })
public class MultiTenantMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiTenantMysqlApplication.class, args);
	}

}
