package service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Authorization authService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/*").permitAll()
                .antMatchers("/user/*").hasRole("USER")
                .antMatchers("/admin/*").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/user/profile",true)
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .and()
            .csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder)
            throws Exception {
        builder.authenticationProvider(authService);
    }
}