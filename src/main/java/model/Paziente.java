package model;


public class Paziente extends Persona {
    private String codiceFiscalePaziente;
    private String condizioniMediche;

    // Costruttore
    public Paziente(PazienteInfo pazienteInfo) {
        super(pazienteInfo.getNome(), pazienteInfo.getCognome(), pazienteInfo.getDataDiNascita(), pazienteInfo.getNumeroTelefonico(), pazienteInfo.getEmail(), pazienteInfo.getPassword());
        this.codiceFiscalePaziente = pazienteInfo.getNumeroTesseraSanitaria();
        this.condizioniMediche = pazienteInfo.getCondizioniMediche();
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
