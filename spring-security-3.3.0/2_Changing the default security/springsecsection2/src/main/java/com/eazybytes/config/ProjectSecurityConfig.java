package com.eazybytes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        /*http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll()); // allows all*/
        /*http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll()); // 403 error even if authenticated or not*/
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
                .requestMatchers("/notices", "/contact", "/error").permitAll());

// below is not recommended for normal web/mobile apps as it shows login form page
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());

//        http.formLogin().disable(); // deprecated


        //usernamePasswordAuthenticationFilter
        //HttpBasicAuthenticationFilter

//        http.formLogin(flc->flc.disable()); //this moves the request ot basic authentication
//        http.httpBasic(hbc->hbc.disable());  // disbling both will always return 403 error

        return http.build();
    }

}
