package startupconfig;

import java.io.Serializable;

public class StartupSettingsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    // Istanza unica della classe (Singleton)
    private static StartupSettingsEntity instance;

    // Variabili per memorizzare le scelte di configurazione
    private boolean colorMode; // Modalità a colori
    private int storageOption; // 0 = RAM, 1 = DB, 2 = File System

    // Costruttore privato per impedire l'istanziazione diretta
    private StartupSettingsEntity() {
        // Impostazioni di default
        this.colorMode = true; // Modalità a colori attiva di default
        this.storageOption = 0; // Salvataggio in RAM di default
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

    // Getter e setter per storageOption
    public int getStorageOption() {
        return storageOption;
    }

    public void setStorageOption(int storageOption) {
        this.storageOption = storageOption;
    }
}
