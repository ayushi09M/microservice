package global.ecommerce.payment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {
    @Value("${spring.datasource.url}")
    public String dbUrl;
    @Value("${spring.datasource.username}")
    public String userName;

    @Value("${spring.datasource.password}")
    public String passWord;

    @Value("${spring.datasource.driver-class-name}")
    public String driverClass;

    @Bean(name = "liquibaseDatasource")
    public DataSource liquibaseDatasource(){
        DataSourceBuilder<?> dataSourceBuilder=DataSourceBuilder.create();
        dataSourceBuilder.url(dbUrl);
        dataSourceBuilder.username(userName);
        dataSourceBuilder.password(passWord);
        dataSourceBuilder.driverClassName(driverClass);

        return dataSourceBuilder.build();
    }
}
