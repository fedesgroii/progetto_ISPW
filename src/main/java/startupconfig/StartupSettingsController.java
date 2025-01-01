package startupconfig;

public class StartupSettingsController {
    // Riferimento all'istanza dell'entità
    private final StartupSettingsEntity config;

    public StartupSettingsController() {
        // Ottiene l'istanza dell'entità (Singleton)
        this.config = StartupSettingsEntity.getInstance();
    }

    /**
     * Elabora le impostazioni fornite dall'utente.
     *
     * @param colorMode      true se è selezionata la modalità a colori, false altrimenti
     * @param saveToDatabase true se deve essere salvato nel database, false altrimenti
     */
    public void processSettings(boolean colorMode, boolean saveToDatabase) {
        config.setColorMode(colorMode);
        config.setSaveToDatabase(saveToDatabase);
    }

}
