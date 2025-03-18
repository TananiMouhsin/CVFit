package com.cvfit.cvfit.Backend.DTOs;


public class AuthResponse {
    private String token;
    private Long userId;
    private String userName;
    private String userEmail;

    public AuthResponse(String token, Long userId, String userName, String userEmail) {
        this.token = token;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public String getToken() { return token; }
    public Long getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getUserEmail() { return userEmail; }
}
