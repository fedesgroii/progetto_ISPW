package login_insert;

public class LoginSpecialistModel {
    private String email;
    private String password;

    // Costruttore
    public LoginSpecialistModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setter
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Metodo per verificare le credenziali (simulazione con dati predefiniti)

    /*
    *
    *  RICORDATI DI VERIFICARE SE TENERLO QUI OPPURE METTERLO DENTRO IL CONTROLLER APP
    *
    *
    public boolean validateCredentials() {
        // Logica per la validazione delle credenziali
        return "test@example.com".equals(email) && "password123".equals(password);
    }*/
}
