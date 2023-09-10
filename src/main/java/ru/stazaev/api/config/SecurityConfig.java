package ru.stazaev.api.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.stazaev.api.security.JwtTokenFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests().requestMatchers("/api/auth/**").permitAll();

//        http.authorizeHttpRequests().requestMatchers("/api/film/**").hasAuthority("USER");
//        http.authorizeHttpRequests().requestMatchers("/api/film/save").hasAuthority("ADMIN");
//        http.authorizeHttpRequests().requestMatchers("/api/film/delete").hasAuthority("ADMIN");
//        http.authorizeHttpRequests().requestMatchers("/api/film/get/**").permitAll();

//        http.authorizeHttpRequests().requestMatchers("/api/selection/**").hasAuthority("USER");
//        http.authorizeHttpRequests().requestMatchers("/api/selection/save").hasAuthority("ADMIN");
//        http.authorizeHttpRequests().requestMatchers("/api/selection/delete").hasAuthority("ADMIN");
//        http.authorizeHttpRequests().requestMatchers("/api/selection/get/**").permitAll();

//        http.authorizeHttpRequests().requestMatchers("/api/user/**").hasAuthority("USER");

        http.authorizeHttpRequests().anyRequest().permitAll();
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
