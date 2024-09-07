package com.utp.api.shared.util;

public interface IJwtTokenProvider {

    String createToken(String username);

    String getUsername(String token);

    boolean validateToken(String token);
}
