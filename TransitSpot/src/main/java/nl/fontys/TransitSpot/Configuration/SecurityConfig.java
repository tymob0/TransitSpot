package nl.fontys.TransitSpot.Configuration;

import lombok.RequiredArgsConstructor;
import nl.fontys.TransitSpot.Filter.CustomAuthenticationFilter;
import nl.fontys.TransitSpot.Filter.CustomAuthorizationFilter;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;

import static org.springframework.http.HttpMethod.*;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login","/users/token/refresh","/users/addUser").permitAll();
        //open
        http.authorizeRequests().antMatchers(GET,"/stations").permitAll();
        http.authorizeRequests().antMatchers(GET,"/stations/count").permitAll();
        http.authorizeRequests().antMatchers(GET,"/stations/{id}").permitAll();
        http.authorizeRequests().antMatchers(GET,"/routes").permitAll();
        http.authorizeRequests().antMatchers(GET,"/routes/{originID}/{destinationID}").permitAll();
        //stations
        http.authorizeRequests().antMatchers(POST,"/stations").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PUT,"/stations").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(DELETE,"/stations").hasAnyAuthority("ROLE_ADMIN");
        //routes
        http.authorizeRequests().antMatchers(POST,"/routes").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PUT,"/routes").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(DELETE,"/routes").hasAnyAuthority("ROLE_ADMIN");
        //tickets
        http.authorizeRequests().antMatchers(GET,"/tickets").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(POST,"/tickets").hasAnyAuthority("ROLE_USER");
        //users
        http.authorizeRequests().antMatchers(GET,"/changePassword/{pass_old}/{pass_new}").hasAnyAuthority("ROLE_USER");
        //statistics
        http.authorizeRequests().antMatchers(GET,"/statistics").permitAll();
        http.authorizeRequests().antMatchers(POST,"/statistics").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PUT,"/statistics").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(DELETE,"/statistics").hasAnyAuthority("ROLE_ADMIN");
        //else
        http.authorizeRequests().antMatchers("/users/check").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER");
        http.authorizeRequests().antMatchers("/ws/**").permitAll();
        http.authorizeRequests().antMatchers(GET,"/routes").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.cors().configurationSource(corsConfigurationSource());

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000/","http://localhost:8080/" )); // <-- you may change "*"
       // configuration.setAllowedOrigins(Arrays.asList("*" )); // <-- you may change "*"
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList(
                "Accept", "Origin", "Content-Type", "Depth", "User-Agent", "If-Modified-Since,",
                "Cache-Control", "Authorization", "X-Req", "X-File-Size", "X-Requested-With", "X-File-Name"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

}
