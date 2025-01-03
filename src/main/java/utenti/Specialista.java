package utenti;

public class Specialista extends Persona {
    private String specializzazione;

    // Costruttore
    public Specialista(String nome, String cognome, String dataDiNascita, String numeroTelefonico, String email, String specializzazione) {
        super(nome, cognome, dataDiNascita, numeroTelefonico, email);
        this.specializzazione = specializzazione;
    }

    // Getter e Setter
    public String getSpecializzazione() {
        return specializzazione;
    }

    public void setSpecializzazione(String specializzazione) {
        this.specializzazione = specializzazione;
    }

    @Override
    public String toString() {
        return super.toString() + ", Specialista{" +
                "specializzazione='" + specializzazione + '\'' +
                '}';
    }
}
