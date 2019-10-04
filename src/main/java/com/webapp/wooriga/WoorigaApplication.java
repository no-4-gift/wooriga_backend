package com.webapp.wooriga;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WoorigaApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="+
            "classpath:/application.yml,"+ "classpath:/mysqlIdentify.yml";

    public static void main(String[] args) {
        try {
            new SpringApplicationBuilder(WoorigaApplication.class)
                    .properties(APPLICATION_LOCATIONS)
                    .run(args);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


}
