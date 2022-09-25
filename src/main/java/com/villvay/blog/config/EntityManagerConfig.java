package com.villvay.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.persistence.EntityManagerFactory;

@Configuration
public class EntityManagerConfig {

//    @Bean(name="entityManagerFactory")
//    public EntityManagerFactory sessionFactory() {
//        return new H2();
//    }
}
