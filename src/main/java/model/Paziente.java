package model;

import java.time.LocalDate;

public class Paziente extends Persona {
    private String codiceFiscalePaziente;
    private String condizioniMediche;

    // Costruttore privato
    private Paziente(Builder builder) {
        super(builder.nome, builder.cognome, LocalDate.parse(builder.dataDiNascita), builder.numeroTelefonico, builder.email, builder.password);
        this.codiceFiscalePaziente = builder.codiceFiscalePaziente;
        this.condizioniMediche = builder.condizioniMediche;
    }

    // Getter
    public String getCodiceFiscalePaziente() {
        return codiceFiscalePaziente;
    }

    public String getCondizioniMediche() {
        return condizioniMediche;
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

    /*
    Le due classi (Paziente e il suo Builder) devono stare nello stesso file.
    Questo perché il Builder è una classe statica interna della classe Paziente
     e non è concepito per esistere come entità separata.
     */

    // Classe statica Builder
    public static class Builder {
        private String nome;
        private String cognome;
        private String dataDiNascita;
        private String numeroTelefonico;
        private String email;
        private String password;
        private String codiceFiscalePaziente;
        private String condizioniMediche;

        // Metodi per impostare i campi
        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder cognome(String cognome) {
            this.cognome = cognome;
            return this;
        }

        public Builder dataDiNascita(LocalDate dataDiNascita) {
            this.dataDiNascita = dataDiNascita.toString(); // Converti LocalDate in String
            return this;
        }


        public Builder numeroTelefonico(String numeroTelefonico) {
            this.numeroTelefonico = numeroTelefonico;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder codiceFiscalePaziente(String codiceFiscalePaziente) {
            this.codiceFiscalePaziente = codiceFiscalePaziente;
            return this;
        }

        public Builder condizioniMediche(String condizioniMediche) {
            this.condizioniMediche = condizioniMediche;
            return this;
        }

        // Metodo per costruire l'oggetto Paziente
        public Paziente build() {
            return new Paziente(this);
        }
    }
}
