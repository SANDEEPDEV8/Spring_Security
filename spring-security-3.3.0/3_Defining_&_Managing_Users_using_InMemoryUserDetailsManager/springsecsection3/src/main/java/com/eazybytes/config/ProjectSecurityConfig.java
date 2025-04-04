package com.eazybytes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
                .requestMatchers("/notices", "/contact", "/error").permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    // UserDetailsManager extends UserDetailsService
    // can add multiples users inmemmory
    @Bean
    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withUsername("user").password("{noop}12345").authorities("read").build();
//        UserDetails admin = User.withUsername("admin").password("{noop}54321").authorities("admin").build();

        //{noop}  >> no password encoder. It will be stored as plain text

        UserDetails user = User.withUsername("user").password("{noop}EazyBank@12345").authorities("read").build(); //NoOpPasswordEncoder
        UserDetails admin = User.withUsername("admin")
                            .password("{bcrypt}$2a$12$tZ1kIoWCuqqZi6wne7ZCMepcb9VBNX2KffQoohGye12lySO.geUa.") //BCryptPasswordEncoder (EasyBank@54321) https://bcrypt-generator.com/
                            .authorities("admin").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
        //better way is use factory which has multiple encoders

        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); // default is BCrypt
    }

    /**
     * From Spring Security 6.3 version
     * prevents you from using weak passwords
     * @return
     */
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker(); //https://api.pwnedpasswords.com/range/
    }


    /*
      UserDetails, User
      Principal, Authentication

      DaoAuthenticationProvider > retrieveUser()
      framework uses InMemoryUserDetailsManager as we configured in above bean and store in hashMap

    */
}
