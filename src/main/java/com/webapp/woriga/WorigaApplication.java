package com.webapp.woriga;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@MapperScan(basePackages= {"com.webapp.woriga.mybatis.mapper"})
public class WorigaApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="+
            "classpath:/application.yml,"+ "classpath:/mysqlIdentify.yml";

    public static void main(String[] args) {
        try {
            new SpringApplicationBuilder(WorigaApplication.class)
                    .properties(APPLICATION_LOCATIONS)
                    .run(args);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
