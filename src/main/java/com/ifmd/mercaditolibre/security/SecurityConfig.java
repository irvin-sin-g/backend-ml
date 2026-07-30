package com.ifmd.mercaditolibre.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // 1. Permitir preflight de CORS en todas las rutas
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // 2. Endpoints públicos
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/productos", "/api/v1/productos/").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/productos/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/categorias/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/proveedores/**").permitAll()

                // 3. Operaciones de escritura/modificación/eliminación (Solo Administrador)
                .requestMatchers(HttpMethod.POST, "/api/v1/productos", "/api/v1/productos/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/productos/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/productos/**").hasAuthority("ROLE_ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/v1/categorias", "/api/v1/categorias/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/categorias/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/categorias/**").hasAuthority("ROLE_ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/v1/proveedores", "/api/v1/proveedores/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/proveedores/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/proveedores/**").hasAuthority("ROLE_ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/v1/clientes/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/clientes/**").hasAuthority("ROLE_ADMIN")

                // 4. Ventas y Compras
                .requestMatchers(HttpMethod.POST, "/api/v1/ventas/procesar").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/ventas/mis-compras").hasAnyAuthority("ROLE_CLIENTE", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/ventas/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/ventas", "/api/v1/ventas/**").hasAnyAuthority("ROLE_CLIENTE", "ROLE_ADMIN")

                // 5. Pagos
                .requestMatchers("/api/v1/pagos/**").permitAll()

                // 6. Cualquier otra petición requiere autenticación
                .anyRequest().authenticated()
            );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Se agregan el origen del deploy remoto y local para desarrollo
        configuration.setAllowedOrigins(Arrays.asList(
            "http://bg6vb4fauvu81h61ssyqtyma.2.24.106.89.sslip.io",
            "http://localhost:5173",
            "http://localhost:3000"
        ));
        
        // Si prefieres permitir cualquier origen durante pruebas, puedes usar setAllowedOriginPatterns:
        // configuration.setAllowedOriginPatterns(Collections.singletonList("*"));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "X-Requested-With"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Link", "X-Total-Count"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
