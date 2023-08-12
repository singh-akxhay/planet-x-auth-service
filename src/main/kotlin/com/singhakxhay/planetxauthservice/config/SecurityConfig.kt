package com.singhakxhay.planetxauthservice.config

import com.singhakxhay.planetxauthservice.exception.AuthExceptionHandler
import com.singhakxhay.planetxauthservice.security.JwtAuthFilter
import com.singhakxhay.planetxauthservice.security.AuthFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig {
    @Autowired
    private lateinit var authExceptionHandler: AuthExceptionHandler

    @Autowired
    private lateinit var authFilter: AuthFilter

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        // Disable CORS
        http.cors { it.disable() }

        // Disable CSRF
        http.csrf { it.disable() }

        // Change to stateless session management
        http.sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

        // Add exception handler
        http.exceptionHandling { it.authenticationEntryPoint(authExceptionHandler) }

        // Authorize http requests
        http.authorizeHttpRequests {
            it.apply {
                requestMatchers("/auth/**").permitAll()
                anyRequest().authenticated()
            }
        }

        // Add Jwt authentication filter
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}