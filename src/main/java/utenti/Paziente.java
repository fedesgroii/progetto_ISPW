package utenti;

public class Paziente extends Persona {
    private String numeroTesseraSanitaria;
    private String condizioniMediche;
    // Costruttore
    public Paziente(String nome, String cognome, String dataDiNascita, String numeroTelefonico, String email, String numeroTesseraSanitaria, String condizioniMediche) {
        super(nome, cognome, dataDiNascita, numeroTelefonico, email);
        this.numeroTesseraSanitaria = numeroTesseraSanitaria;
        this.condizioniMediche = condizioniMediche;
    }

    // Getter e Setter
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

    @Override
    public String toString() {
        return super.toString() + ", Paziente{" +
                "numeroTesseraSanitaria='" + numeroTesseraSanitaria + '\'' +
                ", condizioniMediche='" + condizioniMediche + '\'' +
                '}';
    }
}
