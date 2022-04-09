package be.cmahy.multitenantmysqlimpl.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "tenants")
public class DataSourceProperties {
    private final static Logger log = LoggerFactory.getLogger(DataSourceProperties.class);

    private final Map<Object, Object> datasources = new LinkedHashMap<>();

    public Map<Object, Object> getDataSources() {
        return datasources;
    }

    public void setDataSources(Map<String, Map<String, String>> dataSources) {
        dataSources
            .forEach((key, value) -> {
                log.info("Database {} added", key);

                this.datasources.put(key, convert(value));
            });
    }

    private DataSource convert(Map <String, String> source) {
        return DataSourceBuilder.create()
            .url(source.get("jdbcUrl"))
            .driverClassName(source.get("driverClassName"))
            .username(source.get("username"))
            .password(source.get("password"))
            .build();
    }
}
