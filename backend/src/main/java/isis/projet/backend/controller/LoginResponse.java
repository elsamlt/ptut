package isis.projet.backend.controller;

public class LoginResponse {
    private String token;
    private String email;

    // Constructeur
    public LoginResponse(String token, String email) {
        this.token = token;
        this.email = email;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }
}