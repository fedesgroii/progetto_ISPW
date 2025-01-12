package model;

import java.time.LocalDate;

public abstract class Persona {
    protected String nome;
    protected String cognome;
    protected LocalDate dataDiNascita;
    protected String numeroTelefonico;
    protected String email;
    protected String password;

    // Costruttore
    protected Persona(String nome, String cognome, LocalDate dataDiNascita, String numeroTelefonico, String email, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.numeroTelefonico = numeroTelefonico;
        this.email = email;
        this.password = password;
    }

    // Getter
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public LocalDate getDataDiNascita() { return dataDiNascita; }
    public String getNumeroTelefonico() { return numeroTelefonico; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    // Setter
    public void setNome(String nome) { this.nome = nome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public void setDataDiNascita(LocalDate dataDiNascita) { this.dataDiNascita = dataDiNascita; }
    public void setNumeroTelefonico(String numeroTelefonico) { this.numeroTelefonico = numeroTelefonico; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}
