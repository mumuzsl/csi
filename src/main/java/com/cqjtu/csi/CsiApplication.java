package com.cqjtu.csi;

import com.cqjtu.csi.repository.base.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.cqjtu.csi.repository", repositoryBaseClass = BaseRepositoryImpl.class)
@EnableAsync
@EnableScheduling
public class CsiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsiApplication.class, args);
    }

}
