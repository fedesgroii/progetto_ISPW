import java.io.Serializable;

public class StartupSettingsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    // Istanza unica della classe (Singleton)
    private static StartupSettingsEntity instance;

    // Variabili per memorizzare le scelte di configurazione
    private boolean colorMode; // Modalità a colori
    private boolean saveToDatabase; // Salvataggio nel database

    // Costruttore privato per impedire l'istanziazione diretta
    private StartupSettingsEntity() {
        // Impostazioni di default
        this.colorMode = true; // Modalità a colori attiva di default
        this.saveToDatabase = false; // Salvataggio disattivato di default
    }

    // Metodo statico per ottenere l'istanza unica della classe
    public static StartupSettingsEntity getInstance() {
        if (instance == null) {
            instance = new StartupSettingsEntity();
        }
        return instance;
    }

    // Getter e setter per colorMode
    public boolean isColorMode() {
        return colorMode;
    }

    public void setColorMode(boolean colorMode) {
        this.colorMode = colorMode;
    }

    // Getter e setter per saveToDatabase
    public boolean isSaveToDatabase() {
        return saveToDatabase;
    }

    public void setSaveToDatabase(boolean saveToDatabase) {
        this.saveToDatabase = saveToDatabase;
    }
}