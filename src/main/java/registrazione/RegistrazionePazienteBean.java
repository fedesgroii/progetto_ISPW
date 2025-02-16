package registrazione;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import org.mindrot.jbcrypt.BCrypt; // Libreria per l'hashing delle password

public class RegistrazionePazienteBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nomePazienteBean;
    private String cognomePazienteBean;
    private LocalDate dataDiNascitaPazienteBean;
    private String numeroTelefonicoPazienteBean;
    private String emailPazienteBean;
    private String codiceFiscalePazienteBean;
    private String passwordPazienteBean; // Memorizza la password hashata

    // Costruttore di default
    public RegistrazionePazienteBean() {}

    // Costruttore con parametri
    public RegistrazionePazienteBean(String nomePazienteBean, String cognomePazienteBean,
                                     LocalDate dataDiNascitaPazienteBean, String numeroTelefonicoPazienteBean,
                                     String emailPazienteBean, String codiceFiscalePazienteBean,
                                     String passwordPazienteBean) {
        this.nomePazienteBean = nomePazienteBean;
        this.cognomePazienteBean = cognomePazienteBean;
        this.dataDiNascitaPazienteBean = dataDiNascitaPazienteBean;
        this.numeroTelefonicoPazienteBean = numeroTelefonicoPazienteBean;
        this.emailPazienteBean = emailPazienteBean;
        this.codiceFiscalePazienteBean = codiceFiscalePazienteBean;
        setPasswordPazienteBean(passwordPazienteBean); // La password viene hashata nel setter
    }

    // Getter e Setter senza hashing (tranne per la password)
    public String getNomePazienteBean() { return nomePazienteBean; }
    public void setNomePazienteBean(String nomePazienteBean) { this.nomePazienteBean = nomePazienteBean; }

    public String getCognomePazienteBean() { return cognomePazienteBean; }
    public void setCognomePazienteBean(String cognomePazienteBean) { this.cognomePazienteBean = cognomePazienteBean; }

    public LocalDate getDataDiNascitaPazienteBean() { return dataDiNascitaPazienteBean; }
    public void setDataDiNascitaPazienteBean(LocalDate dataDiNascitaPazienteBean) { this.dataDiNascitaPazienteBean = dataDiNascitaPazienteBean; }

    public String getNumeroTelefonicoPazienteBean() { return numeroTelefonicoPazienteBean; }
    public void setNumeroTelefonicoPazienteBean(String numeroTelefonicoPazienteBean) { this.numeroTelefonicoPazienteBean = numeroTelefonicoPazienteBean; }

    public String getEmailPazienteBean() { return emailPazienteBean; }
    public void setEmailPazienteBean(String emailPazienteBean) { this.emailPazienteBean = emailPazienteBean; }

    public String getCodiceFiscalePazienteBean() { return codiceFiscalePazienteBean; }
    public void setCodiceFiscalePazienteBean(String codiceFiscalePazienteBean) { this.codiceFiscalePazienteBean = codiceFiscalePazienteBean; }

    // Getter per la password (ritorna la password hashata)
    public String getPasswordPazienteBean() { return passwordPazienteBean; }

    // Setter per la password: applica l'hashing prima di memorizzarla
    public void setPasswordPazienteBean(String passwordPazienteBean) {
        if (passwordPazienteBean == null) {
            throw new IllegalArgumentException("PasswordPazienteBean non può essere nulla");
        }
        // Applica l'hashing della password usando BCrypt
        this.passwordPazienteBean = BCrypt.hashpw(passwordPazienteBean, BCrypt.gensalt());
    }

    // Metodo per verificare una password inserita rispetto a quella hashata
    public boolean verificaPasswordPazienteBean(String passwordInserita) {
        // Usa BCrypt per confrontare la password inserita con quella hashata
        return BCrypt.checkpw(passwordInserita, this.passwordPazienteBean);
    }

    @Override
    public String toString() {
        return "RegistrazionePazienteBean{" +
                "nomePazienteBean='" + nomePazienteBean + '\'' +
                ", cognomePazienteBean='" + cognomePazienteBean + '\'' +
                ", dataDiNascitaPazienteBean=" + dataDiNascitaPazienteBean +
                ", numeroTelefonicoPazienteBean='" + numeroTelefonicoPazienteBean + '\'' +
                ", emailPazienteBean='" + emailPazienteBean + '\'' +
                ", codiceFiscalePazienteBean='" + codiceFiscalePazienteBean + '\'' +
                ", passwordPazienteBean='[PROTECTED]'" + // Non mostrare la password hashata
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrazionePazienteBean that = (RegistrazionePazienteBean) o;
        return Objects.equals(emailPazienteBean, that.emailPazienteBean) &&
                Objects.equals(codiceFiscalePazienteBean, that.codiceFiscalePazienteBean);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailPazienteBean, codiceFiscalePazienteBean);
    }
}