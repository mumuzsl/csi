package com.cqjtu.csi;

import com.cqjtu.csi.repository.base.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.cqjtu.csi.repository", repositoryBaseClass = BaseRepositoryImpl.class)
public class CsiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsiApplication.class, args);
    }

}
