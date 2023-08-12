package com.singhakxhay.planetxauthservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtConfig {
    @Value("\${jwt.secret}")
    lateinit var secret: String

    @Value("\${jwt.expiration}")
    lateinit var expiration: String

    constructor(secret: String, expiration: String) {
        this.secret = secret
        this.expiration = expiration
    }

    constructor()
}
