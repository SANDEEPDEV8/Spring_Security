package com.eazybytes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
//@EnableWebSecurity // this is optional for springboot environment. use for normal spring projects only
/*@EnableJpaRepositories("com.eazybytes.repository")
@EntityScan("com.eazybytes.model")*/ // only use if enitity, repositories are not in the main package
public class EazyBankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EazyBankBackendApplication.class, args);
    }

}
