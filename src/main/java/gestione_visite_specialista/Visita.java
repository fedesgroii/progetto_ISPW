package gestione_visite_specialista;

import javafx.scene.control.Button;

// Classe per rappresentare una visita
public class Visita {
    private String nome;
    private String cognome;
    private String data;
    private String ora;
    private Button bottoneElimina;

    public Visita(String nome, String cognome, String data, String ora) {
        this.nome = nome;
        this.cognome = cognome;
        this.data = data;
        this.ora = ora;
        this.bottoneElimina = new Button("Elimina");
        this.bottoneElimina.setOnAction(e -> {
            // Logica per eliminare la visita
            System.out.println("Eliminata visita di " + nome + " " + cognome);
        });
    }

    // Getter e Setter
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getOra() { return ora; }
    public void setOra(String ora) { this.ora = ora; }

    public Button getBottoneElimina() { return bottoneElimina; }
    public void setBottoneElimina(Button bottoneElimina) { this.bottoneElimina = bottoneElimina; }
}
