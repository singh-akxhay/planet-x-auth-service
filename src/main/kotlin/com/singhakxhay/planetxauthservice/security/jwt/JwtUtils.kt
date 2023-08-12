package com.singhakxhay.planetxauthservice.security.jwt

import com.singhakxhay.planetxauthservice.config.JwtConfig
import com.singhakxhay.planetxauthservice.exception.JwtException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


private const val PLANET_X_AUTH_SERVICE = "Planet-X-Auth-Service"

@Component
class JwtUtils(private val jwtConfig: JwtConfig) {
    private fun parseToken(token: String): Claims {
        // Create JwtParser
        val jwtParser: JwtParser = Jwts.parserBuilder()
            .setSigningKey(jwtConfig.secret.toByteArray())
            .build()

        return try {
            jwtParser.parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            throw JwtException("Token expired")
        } catch (e: Exception) {
            throw JwtException("Invalid token format")
        }
    }

    fun validateToken(token: String) {
        parseToken(token)
    }

    fun getUsernameFromToken(token: String): String {
        // Get claims
        val claims = parseToken(token)

        // Extract subject
        return claims.subject
    }

    fun generateToken(username: String, issuedAt: LocalDateTime): String {
        // Generate token
        val expiration = issuedAt.plusDays(jwtConfig.expiration.toLong())
        return Jwts.builder()
            .setSubject(username)
            .setIssuer(PLANET_X_AUTH_SERVICE)
            .setIssuedAt(issuedAt.toDate())
            .setExpiration(expiration.toDate())
            .signWith(secretKey)
            .compact()
    }

    private val secretKey by lazy { Keys.hmacShaKeyFor(jwtConfig.secret.toByteArray()) }
}

private fun LocalDateTime.toDate(): Date = Date.from(this.atZone(ZoneId.systemDefault()).toInstant())