package com.webapp.woriga;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.client.RestTemplate;
import javax.sql.DataSource;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
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

    public static final String APPLICATION_LOCATIONS = "spring.config.location="+
            "classpath:/application.yml,"+ "classpath:/mysqlIdentify.yml";

}
