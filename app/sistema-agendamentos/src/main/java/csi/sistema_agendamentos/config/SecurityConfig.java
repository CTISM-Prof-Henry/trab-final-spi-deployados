package csi.sistema_agendamentos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity http) throws Exception {
        http
                // 1. Habilita a configuração de CORS definida no bean 'corsConfigurationSource'
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 2. Desabilita a proteção CSRF, comum para APIs REST
                .csrf( AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        // 3. Permite todas as requisições (ajuste conforme necessário)
                        .anyRequest().permitAll()
                );
        return http.build();
    }

    // 4. Bean que define a configuração do CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Define as origens permitidas (seu frontend Angular)
        configuration.setAllowedOrigins( Arrays.asList("http://localhost:4200"));
        // Define os métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Define os cabeçalhos permitidos
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // Permite o envio de credenciais (cookies, etc.)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica para todos os paths
        return source;
    }
}
