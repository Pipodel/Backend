package com.portfolio.Delaporte.Security;

import com.portfolio.Delaporte.Security.Service.UserDetailsImpl;
import com.portfolio.Delaporte.Security.jwt.JwtEntryPoint;
import com.portfolio.Delaporte.Security.jwt.JwtTokenFilter;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurity extends WebSecurityConfigurerAdapter{
    @Autowired
    UserDetailsImpl userDetailsServicesImpl;
    
    @Autowired
    JwtEntryPoint jwtEntryPoint;
    
    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);           
    }
    */
     @Override
    protected void configure(HttpSecurity http) throws Exception {


        List<String> list1 = Arrays.asList(new String[]{"Authorization", "Cache-Control", "Content-Type"});
        List<String> list2 = Arrays.asList(new String[]{"https://frontenddelaporte.web.app"});
        List<String> list3 = Arrays.asList(new String[]{"GET", "POST", "PUT", "DELETE", "OPTIONS"});
        List<String> list4 = Arrays.asList(new String[]{"Authorization"});

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(list1);
        corsConfiguration.setAllowedOrigins(list2);
        corsConfiguration.setAllowedMethods(list3);
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(list4);

        http.csrf().disable();
        http.authorizeRequests().antMatchers("**").permitAll();
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors().configurationSource(request -> corsConfiguration);
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }
    
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailsServicesImpl).passwordEncoder(passwordEncoder());
    }
    
    
    
}