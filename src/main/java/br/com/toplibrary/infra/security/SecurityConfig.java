package br.com.toplibrary.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .headers().frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                .and()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/login", HttpMethod.POST.name())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/users", HttpMethod.POST.name())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/books", HttpMethod.GET.name())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/books/**", HttpMethod.GET.name())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui.html")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/genres")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/genres/**")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/authors")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/authors/**")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/publishing-company")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/publishing-company/**")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/books", HttpMethod.POST.name())).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/books/**", HttpMethod.DELETE.name())).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/books/**", HttpMethod.PUT.name())).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/rentals/**", HttpMethod.PUT.name())).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/users/admin")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/users", HttpMethod.GET.name())).hasRole("ADMIN")
                       .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
