package fact.it.apigateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity
                .authorizeExchange(exchange ->
                        exchange.pathMatchers(HttpMethod.GET,"/recipes")
                                .permitAll().anyExchange().authenticated()
                                /*.pathMatchers(HttpMethod.POST,"/recipes").authenticated()
                                .pathMatchers(HttpMethod.PUT,"/recipes/**").authenticated()
                                .pathMatchers(HttpMethod.DELETE,"/recipes/**").authenticated()
                                .pathMatchers(HttpMethod.GET,"/ingredients").permitAll()
                                .pathMatchers(HttpMethod.GET,"/ratings").permitAll()
                                .pathMatchers(HttpMethod.GET,"/users").permitAll()*/
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(withDefaults())
                );
        return serverHttpSecurity.build();
    }
}
