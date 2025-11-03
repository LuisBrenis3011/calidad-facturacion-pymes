package com.billtel.calidad.facturacion_pymes.layer.auth;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public class TokenJwtConfig {

    private TokenJwtConfig() {
        throw new UnsupportedOperationException("Esta clase no debe instanciarse");
    }
//    public static final String SECRET = "mi_clave_super_secreta_para_el_token_1234567890_abcdefghijk"; // debe tener al menos 32 chars
    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";

}
