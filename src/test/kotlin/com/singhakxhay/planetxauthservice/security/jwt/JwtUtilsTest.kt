package com.singhakxhay.planetxauthservice.security.jwt

import com.singhakxhay.planetxauthservice.config.JwtConfig
import com.singhakxhay.planetxauthservice.exception.JwtException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

private const val JWT_SECRET = "sIoVC8OFOgmxbk9XRYtY2zMKXuYXBGL2d3x1IV37"
private const val JWT_EXPIRATION = "1"

private const val JWT_TOKEN =
    "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0LXVzZXJuYW1lIiwiaXNzIjoiUGxhbmV0LVgtQXV0aC1TZXJ2aWNlIiwiaWF0IjoxNjkxODE5MDQwLCJleHAiOjE2OTE5MDU0NDB9.1GLGXDCzlgapDoUg4v48bnIM5aGKepEFY-MI7cmRERk"

private const val TAMPERED_JWT_TOKEN =
    "eyJhbGciOiJIUzI1NiJ9.eyDzdWIiOiJ0ZXN0LXVzZXJuYW1lIiwiaXNzIjoiUGxhbmV0LVgtQXV0aC1TZXJ2aWNlIiwiaWF0IjoxNjkxODE5MDQwLCJleHAiOjE2OTE5MDU0NDB9.1GLGXDCzlgapDoUg4v48bnIM5aGKepEFY-MI7cmRERk"

private const val EXPIRED_JWT_TOKEN =
    "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0LXVzZXJuYW1lIiwiaXNzIjoiUGxhbmV0LVgtQXV0aC1TZXJ2aWNlIiwiaWF0IjoxNDkxODE5MDQwLCJleHAiOjE1OTE5MDU0NDB9.C3JH8u2GutQV7s-q-pNeRb83tC1IG9oLZQf-0jmoB2w"

private const val USERNAME = "test-username"

class JwtUtilsTest {

    private val jwtUtils: JwtUtils = JwtUtils(JwtConfig(JWT_SECRET, JWT_EXPIRATION))

    @Test
    fun givenUsernameAndIssuedAt_whenGenerateTokenIsCalled_thenReturnJwtToken() {
        val issuedAt = LocalDateTime.of(2023, Month.AUGUST, 12, 11, 14)
        val actualToken = jwtUtils.generateToken(USERNAME, issuedAt)

        Assertions.assertEquals(JWT_TOKEN, actualToken)
    }

    @Test
    fun givenJwtToken_whenGetUsernameFromToken_thenReturnUsername() {
        val actualUsername = jwtUtils.getUsernameFromToken(JWT_TOKEN)

        Assertions.assertEquals(USERNAME, actualUsername)
    }

    @Test
    fun givenJwtToken_whenValidateToken_thenValidationSuccessful() {
        Assertions.assertDoesNotThrow { jwtUtils.validateToken(JWT_TOKEN) }
    }

    @Test
    fun givenTamperedJwtToken_whenValidateToken_thenValidationUnsuccessful() {
        Assertions.assertThrows(JwtException::class.java) { jwtUtils.validateToken(TAMPERED_JWT_TOKEN) }
    }

    @Test
    fun givenExpiredJwtToken_whenValidateToken_thenValidationUnsuccessful() {
        Assertions.assertThrows(JwtException::class.java) { jwtUtils.validateToken(EXPIRED_JWT_TOKEN) }
    }
}
