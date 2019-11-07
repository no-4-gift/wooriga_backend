package com.webapp.wooriga;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WoorigaApplication {
    public static final String APPLICATION_LOCATIONS = "spring.config.location="+
            "classpath:/application.yml,"+ "classpath:/mysqlIdentify.yml," + "classpath:/aws.yml";
    public static void main(String[] args) {
        try{
            new SpringApplicationBuilder(WoorigaApplication.class)
                    .properties(APPLICATION_LOCATIONS)
                    .run(args);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


}