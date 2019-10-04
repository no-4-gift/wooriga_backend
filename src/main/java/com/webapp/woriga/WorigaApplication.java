package com.webapp.woriga;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.client.RestTemplate;
import javax.sql.DataSource;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@MapperScan(basePackages= {"com.webapp.woriga.mybatis.mapper"})
public class WorigaApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorigaApplication.class, args);
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage("com.webapp.woriga.mybatis.vo");
      
    public static final String APPLICATION_LOCATIONS = "spring.config.location="+
            "classpath:/application.yml,"+ "classpath:/mysqlIdentify.yml";

}
