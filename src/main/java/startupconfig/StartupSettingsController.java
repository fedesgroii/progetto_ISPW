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
     * @param colorMode       true se è selezionata la modalità a colori, false altrimenti
     * @param storageOption   modalità di salvataggio:
     *                        0 = Memoria RAM (Stateless)
     *                        1 = Database Locale
     *                        2 = File System
     */
    public void processSettings(boolean colorMode, int storageOption) {
        config.setColorMode(colorMode);
        config.setStorageOption(storageOption);

        // Se è selezionata l'opzione di salvataggio nel database, avvia MySQL
        if (storageOption == 1) {
            AvviaMySQL.eseguiComando("sudo /usr/local/mysql/support-files/mysql.server start");
        }
    }
}
