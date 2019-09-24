package com.webapp.woriga;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@SpringBootApplication
@MapperScan(basePackages= {"com.webapp.woriga.mybatis.mapper"})
public class WorigaApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorigaApplication.class, args);
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage("com.webapp.woriga.mybatis.vo");

        //특정 경로나 위치에서 자원(파일)을 읽어 오는 객체
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/*Mapper.xml"));
        return sessionFactory.getObject();
    }
}
