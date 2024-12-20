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

        // Eventuali altre logiche legate alle impostazioni possono essere aggiunte qui.
        System.out.println("Configurazione aggiornata:");
        System.out.println("Modalità a Colori: " + (colorMode ? "Attiva" : "Disattiva"));
        System.out.println("Salvataggio nel Database: " + (saveToDatabase ? "Abilitato" : "Disabilitato"));
    }
}
