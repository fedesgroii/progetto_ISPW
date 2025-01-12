package utenti;

import com.sun.javafx.binding.SelectBinding;

import java.time.LocalDate;

public class Specialista extends Persona {
    private String specializzazione;

    // Costruttore
    public Specialista(String nome, String cognome, LocalDate dataDiNascita, String numeroTelefonico, String email, String specializzazione, String password) {
        super(nome, cognome, dataDiNascita, numeroTelefonico, email,password);
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
