package com.dateunrepaso.dur.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.dateunrepaso.dur.servicios.UserDetailsServiceImp;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /*
     * @Bean
     * public DefaultSecurityFilterChain SecurityFilterChain(HttpSecurity
     * httpSecurity) throws Exception {
     * return httpSecurity
     * .csrf(csrf -> csrf.disable())
     * .httpBasic(Customizer.withDefaults())
     * .sessionManagement(session ->
     * session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
     * .authorizeHttpRequests(http -> {
     * //Configurar endpoints publicos
     * http.requestMatchers(HttpMethod.GET, "/auth-no").permitAll();
     * 
     * //Configurar endpoints privados
     * http.requestMatchers(HttpMethod.GET,
     * "/auth-si/prueba").hasAnyAuthority("CREATE");
     * 
     * //Configurar endpoints no especificados
     * http.anyRequest().denyAll(); //Rechaza todo
     * })
     * .build();
     * }
     */


//https://www.youtube.com/watch?v=0wTsLRxS3gA&ab_channel=LaTecnolog%C3%ADaAvanza HACER QUE REDIRIGA EL LOGIN
     @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> http
                        .requestMatchers("/", "/login", "/registro", "/logout", "/build/css/**", "/build/img/**",
                                "/js/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/app").authenticated()
                        .anyRequest().denyAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/app", true)
                        .permitAll())
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return httpSecurity.build();
    }   

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImp userDetailsServiceImp) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsServiceImp);
        return provider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> userDetails = new ArrayList<>();

        userDetails.add(User.withUsername("santi")
                .password("prueba")
                .roles("ADMIN")
                .authorities("CREATE")
                .build());

        userDetails.add(User.withUsername("dani")
                .password("prueba")
                .roles("ADMIN")
                .authorities("READ")
                .build());

        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /*
     * @Bean
     * public PasswordEncoder passwordEncoder() {
     * return new BCryptPasswordEncoder();
     * }
     */

}
