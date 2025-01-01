package com.ansimtouchserver.global.jwt.exception

enum class JwtErrorType {
    OK,
    ExpiredJwtException,
    SignatureException,
    MalformedJwtException,
    UnsupportedJwtException,
    IllegalArgumentException,
    UNKNOWN_EXCEPTION
}