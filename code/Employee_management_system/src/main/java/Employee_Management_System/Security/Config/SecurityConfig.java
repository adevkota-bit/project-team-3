package Employee_Management_System.Security.Config;

import Employee_Management_System.Security.Filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static Employee_Management_System.credential.Role.ADMIN;
import static Employee_Management_System.credential.Role.STAFF;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .requestMatchers(
                        "/api/v1/auth/**",
                        "/index*",
                        "/static/**",
                        "/*.js",
                        "/*.json",
                        "/*.ico")

                .permitAll()
                .requestMatchers("/admin/**").hasAnyRole(ADMIN.name())
                .requestMatchers(GET, "/admin/**").hasAnyAuthority(ADMIN.name())
                .requestMatchers(POST, "/admin/**").hasAnyAuthority(ADMIN.name())
                .requestMatchers(PUT, "/admin/**").hasAnyAuthority(ADMIN.name())
                .requestMatchers(DELETE, "/admin/**").hasAnyAuthority(ADMIN.name())

                .requestMatchers("/staff").hasAnyRole(ADMIN.name(), STAFF.name())
                .requestMatchers(GET, "/staff/**").hasAnyAuthority(ADMIN.name(), STAFF.name())
                .requestMatchers(POST, "/staff/**").hasAnyAuthority(ADMIN.name(), STAFF.name())
                .requestMatchers(PUT, "/staff/**").hasAnyAuthority(ADMIN.name(), STAFF.name())
                .requestMatchers(DELETE, "/staff/**").hasAnyAuthority(ADMIN.name(), STAFF.name())

                //.requestMatchers()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().loginPage("/index.html")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/homepage.html", true)
                .failureUrl("/index.html?error=true")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                //.logout()
                //.logoutUrl("/api/v1/auth/logout")
                //.addLogoutHandler(logoutHandler)
                //.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        ;

        return http.build();
    }
}
