package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDate;

@JsonDeserialize(builder = Specialista.Builder.class)
public class Specialista extends Persona {
    private final String specializzazione;

    public Specialista(String nome, String cognome, LocalDate dataDiNascita, String numeroTelefonico, String email, String specializzazione, String password) {
        super(nome, cognome, dataDiNascita, numeroTelefonico, email, password);
        this.specializzazione = specializzazione;
    }

    public String getSpecializzazione() {
        return specializzazione;
    }

    @Override
    public String toString() {
        return super.toString() + ", Specialista{" +
                "specializzazione='" + specializzazione + '\'' +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private String nome;
        private String cognome;
        private LocalDate dataDiNascita; // Cambiato da String a LocalDate
        private String numeroTelefonico;
        private String email;
        private String specializzazione;
        private String password;

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

        @JsonProperty("specializzazione")
        public Builder specializzazione(String specializzazione) {
            this.specializzazione = specializzazione;
            return this;
        }

        @JsonProperty("password")
        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Specialista build() {
            return new Specialista(nome, cognome, dataDiNascita, numeroTelefonico, email, specializzazione, password);
        }
    }
}
