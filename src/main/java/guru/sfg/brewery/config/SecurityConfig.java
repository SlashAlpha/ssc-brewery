package guru.sfg.brewery.config;

import guru.sfg.brewery.security.SFGPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorize -> {
            authorize.antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                    .antMatchers("/beers/find", "/beers*").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                    .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
        })
                .authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();

    }

    @Bean
    PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//        return new LdapShaPasswordEncoder();
//        return new StandardPasswordEncoder();
//        return  new BCryptPasswordEncoder(10);
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return SFGPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails admin= User.withDefaultPasswordEncoder()
//                .username("spring")
//                .password("spring")
//                .roles("ADMIN")
//                .build();
//        UserDetails user= User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("user")
//                .roles("USER")
//                .build();
//                return new InMemoryUserDetailsManager(admin,user);
//                }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("spring")
//                .password("{SSHA}xi2gA2H7CzFoKIDu39i/TNCbVIga8oa9yH2fCA==")
                .password("{bcrypt}$2a$10$QIRggA31NpP5ngZheGO2vOFeHU1No/0Piw6RYfy4d2ZFUqcM2b6ry")
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password("{sha256}457a6904fcb08b50eb08abc23e28a8ece19bbed521b268f829b68f8f9a229619b6dded2033493adc")
                .roles("USER")
                .and()
                .withUser("scott")
                .password("{ldap}{SSHA}r/C4A2mLIyfTDNVcpY6bj55YORFzTBHr3hGq0w==")
                .roles("CUSTOMER");
    }
}
