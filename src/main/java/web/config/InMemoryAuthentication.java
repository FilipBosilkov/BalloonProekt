package web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class InMemoryAuthentication extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/assets/**","/register", "/balloons").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll()
                    .failureUrl("/login?error=BadCredentials")
                    .defaultSuccessUrl("/balloons",true)
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("e-shop")
                .logoutSuccessUrl("/login")
                .and().exceptionHandling().accessDeniedPage("/access_denied");
    }
}
