package utenti;

public abstract class Persona {
    private String nome;
    private String cognome;
    private String dataDiNascita;
    private String numeroTelefonico;
    private String email;

    // Costruttore
    protected Persona(String nome, String cognome, String dataDiNascita, String numeroTelefonico, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.numeroTelefonico = numeroTelefonico;
        this.email = email;
    }

    public String getNome() { return nome; }

    public String getCognome() {
        return cognome;
    }

    public String getDataDiNascita() {
        return dataDiNascita;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public String getEmail() {
        return email;
    }
}


