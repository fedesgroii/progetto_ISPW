package registrazione;
// view -> CG -> crea BEAN -> CA
import storage_db.DatabaseStorageStrategyPaziente;  // Importa la strategia di salvataggio su database per i pazienti
import storage_file.FileManagerPazienti;  // Importa la classe per il salvataggio su file system
import storage_liste.ListaPazienti;
import startupconfig.StartupSettingsEntity;  // Importa la classe per gestire le configurazioni iniziali
import model.Paziente;  // Importa la classe Paziente

public class RegistrazionePazienteControllerApplicativo {
    private final StartupSettingsEntity config = StartupSettingsEntity.getInstance();  // Ottiene l'istanza della configurazione globale
    private final DatabaseStorageStrategyPaziente pazienteDatabase = new DatabaseStorageStrategyPaziente();  // Crea un'istanza per il salvataggio su database

    public RegistrazionePazienteControllerApplicativo() {
    }


    // Metodo dedicato per salvare il paziente in base all'opzione di storage
    boolean savePaziente(Paziente paziente) {
        int storageOption = config.getStorageOption(); // Ottiene l'opzione di salvataggio

        switch (storageOption) {
            case 1: // Salvataggio su Database
                DatabaseStorageStrategyPaziente storage = new DatabaseStorageStrategyPaziente();
                if (storage.trova(paziente).isPresent()) {
                    return false;
                }
                return pazienteDatabase.salva(paziente);

            case 2: // Salvataggio su File System
                FileManagerPazienti fileManager = new FileManagerPazienti(); // Gestione file system
                if (FileManagerPazienti.trovaCodiceFiscaleNellaCartellaPazienti(paziente.getCodiceFiscalePaziente())) {
                    return false;
                }
                return fileManager.salva(paziente);

            default: // Salvataggio in Lista (RAM)
                ListaPazienti pazienteLista = ListaPazienti.getIstanzaListaPazienti();
                if (pazienteLista.isDuplicateInList(paziente, pazienteLista)) {
                    return false;
                }
                pazienteLista.aggiungiPaziente(paziente);
                return true;
        }
    }

    Paziente createPaziente(RegistrazionePazienteBean registrazionePazienteBean) {
        if (registrazionePazienteBean == null) {
            throw new IllegalArgumentException("RegistrazionePazienteBean non può essere null");
        }

        return new Paziente.Builder()
                .nome(registrazionePazienteBean.getNomePazienteBean())
                .cognome(registrazionePazienteBean.getCognomePazienteBean())
                .dataDiNascita(registrazionePazienteBean.getDataDiNascitaPazienteBean())
                .numeroTelefonico(registrazionePazienteBean.getNumeroTelefonicoPazienteBean())
                .email(registrazionePazienteBean.getEmailPazienteBean())
                .password(registrazionePazienteBean.getPasswordPazienteBean())
                .codiceFiscalePaziente(registrazionePazienteBean.getCodiceFiscalePazienteBean())
                .condizioniMediche("DA SPECIFICARE") // Valore predefinito per le condizioni mediche
                .build();
    }

}