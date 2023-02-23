package com.example.login2.security;

import com.example.login2.users.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true
)
public class WebSecurity  {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;


    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
         http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

         http
                .authorizeHttpRequests()
                .requestMatchers(antMatcher(toH2Console().toString())).permitAll()
                 .requestMatchers(antMatcher("/**")).permitAll()
                 //.requestMatchers(antMatcher("/admin/**")).hasAnyRole("ROLE_" + Roles.ADMIN,"ROLE_"+Roles.OWNER)
                 .requestMatchers(antMatcher("/app/**")).hasRole("ROLE_" + Roles.REGISTERED)
                 /*.requestMatchers(antMatcher("/blog/**")).hasRole("ROLE_EDITOR")
                 .requestMatchers(antMatcher("/dev-tools/**")).hasAnyAuthority("DEV_READ","DEV_DELETE")
                 .requestMatchers(antMatcher("/dev-tools-bis/**")).hasAnyAuthority("DO-DEV-TOOLS-BIS")
                 */
                 .anyRequest().authenticated();


         http.csrf().disable();
         http.headers().frameOptions().disable();
         http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
         return http.build();

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }





}