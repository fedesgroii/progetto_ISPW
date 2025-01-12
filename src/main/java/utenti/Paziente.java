package utenti;

import java.time.LocalDate;

public class Paziente extends Persona {
    private String codiceFiscalePaziente;
    private String condizioniMediche;

    // Costruttore
    public Paziente(String nome, String cognome, LocalDate dataDiNascita, String numeroTelefonico, String email, String password, String numeroTesseraSanitaria, String condizioniMediche) {
        super(nome, cognome, dataDiNascita, numeroTelefonico, email, password);
        this.codiceFiscalePaziente = numeroTesseraSanitaria;
        this.condizioniMediche = condizioniMediche;
    }

    // Getter e Setter
    public String getCodiceFiscalePaziente() {
        return codiceFiscalePaziente;
    }

    public void setCodiceFiscalePaziente(String codiceFiscalePaziente) {
        this.codiceFiscalePaziente = codiceFiscalePaziente;
    }

    public String getCondizioniMediche() {
        return condizioniMediche;
    }

    public void setCondizioniMediche(String condizioniMediche) {
        this.condizioniMediche = condizioniMediche;
    }

    @Override
    public String toString() {
        return "Paziente{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataDiNascita=" + dataDiNascita +
                ", numeroTelefonico='" + numeroTelefonico + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", codiceFiscalePaziente='" + codiceFiscalePaziente + '\'' +
                ", condizioniMediche='" + condizioniMediche + '\'' +
                '}';
    }

}
