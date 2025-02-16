package login_inserimento_dati;

public class LoginBean {
    // Campi privati
    private boolean loginSuccess; // Stato del login (true = successo, false = fallimento)
    private String userType; // Tipo di utente (es. "Patient" o "Specialist")
    private String emailBean; // Email dell'utente
    private String passwordBean; // Password dell'utente

    // Costruttore vuoto (richiesto dalle convenzioni JavaBeans)
    public LoginBean() {
        this.loginSuccess = false;
        this.userType = "";
        this.emailBean = "";
        this.passwordBean = "";
    }

    // Getter e Setter per loginSuccess
    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        this.loginSuccess = loginSuccess;
    }

    // Getter e Setter per userType
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    // Getter e Setter per emailBean
    public String getEmailBean() {
        return emailBean;
    }

    public void setEmailBean(String emailBean) {
        this.emailBean = emailBean;
    }

    // Getter e Setter per passwordBean
    public String getPasswordBean() {
        return passwordBean;
    }

    public void setPasswordBean(String passwordBean) {
        this.passwordBean = passwordBean;
    }

    // Metodo per resettare lo stato del bean
    public void reset() {
        this.loginSuccess = false;
        this.userType = "";
        this.emailBean = "";
        this.passwordBean = "";
    }
}