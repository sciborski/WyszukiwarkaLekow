package com.example.dariusz.wyszukiwarkalekow.data.dto;





public class LoginResponseDTO {

    private String access_token;
    private int expires_in;
    private String token_type;
    private String scope;
    private String refresh_token;

    public LoginResponseDTO() {}

    public String getAccessToken() {
        return access_token;
    }

    public int getExpiresIn() {
        return expires_in;
    }

    public String getTokenType() {
        return token_type;
    }

    public String getScope() {
        return scope;
    }

    public String getRefreshToken() {
        return refresh_token;
    }
}
