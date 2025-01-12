package utenti;

import java.time.LocalDate;

// Classe di supporto per ridurre il numero di parametri nel costruttore
public class PazienteInfo {
    private String nome;
    private String cognome;
    private LocalDate dataDiNascita;
    private String numeroTelefonico;
    private String email;
    private String password;
    private String numeroTesseraSanitaria;
    private String condizioniMediche;

    // Costruttore
    public PazienteInfo(String nome, String cognome, LocalDate dataDiNascita, String numeroTelefonico, String email, String password, String numeroTesseraSanitaria, String condizioniMediche) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.numeroTelefonico = numeroTelefonico;
        this.email = email;
        this.password = password;
        this.numeroTesseraSanitaria = numeroTesseraSanitaria;
        this.condizioniMediche = condizioniMediche;
    }

    // Getter e Setter
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumeroTesseraSanitaria() {
        return numeroTesseraSanitaria;
    }

    public void setNumeroTesseraSanitaria(String numeroTesseraSanitaria) {
        this.numeroTesseraSanitaria = numeroTesseraSanitaria;
    }

    public String getCondizioniMediche() {
        return condizioniMediche;
    }

    public void setCondizioniMediche(String condizioniMediche) {
        this.condizioniMediche = condizioniMediche;
    }
}
