package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDate;

@JsonDeserialize(builder = Paziente.Builder.class)
public class Paziente extends Persona {
    private String codiceFiscalePaziente;
    private String condizioniMediche;

    // Costruttore privato
    private Paziente(Builder builder) {
        // Utilizza direttamente builder.dataDiNascita (che è di tipo LocalDate)
        super(builder.nome, builder.cognome, builder.dataDiNascita, builder.numeroTelefonico, builder.email, builder.password);
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

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private String nome;
        private String cognome;
        private LocalDate dataDiNascita; // Cambiato da String a LocalDate
        private String numeroTelefonico;
        private String email;
        private String password;
        private String codiceFiscalePaziente;
        private String condizioniMediche;

        @JsonProperty("nome")
        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        @JsonProperty("cognome")
        public Builder cognome(String cognome) {
            this.cognome = cognome;
            return this;
        }

        @JsonProperty("dataDiNascita")
        public Builder dataDiNascita(LocalDate dataDiNascita) {
            this.dataDiNascita = dataDiNascita;
            return this;
        }

        @JsonProperty("numeroTelefonico")
        public Builder numeroTelefonico(String numeroTelefonico) {
            this.numeroTelefonico = numeroTelefonico;
            return this;
        }

        @JsonProperty("email")
        public Builder email(String email) {
            this.email = email;
            return this;
        }

        @JsonProperty("password")
        public Builder password(String password) {
            this.password = password;
            return this;
        }

        @JsonProperty("codiceFiscalePaziente")
        public Builder codiceFiscalePaziente(String codiceFiscalePaziente) {
            this.codiceFiscalePaziente = codiceFiscalePaziente;
            return this;
        }

        @JsonProperty("condizioniMediche")
        public Builder condizioniMediche(String condizioniMediche) {
            this.condizioniMediche = condizioniMediche;
            return this;
        }

        public Paziente build() {
            return new Paziente(this);
        }
    }
}
