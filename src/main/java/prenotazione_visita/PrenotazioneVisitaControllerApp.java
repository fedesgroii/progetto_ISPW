package prenotazione_visita;
import model.Visita;
import startupconfig.StartupSettingsEntity;
import storage_db.DatabaseStorageStrategyVisita;
import storage_file.FileManagerVisite; // Nuovo import per la gestione del file system
import storage_liste.ListaVisite;
public class PrenotazioneVisitaControllerApp {
    private final StartupSettingsEntity config = StartupSettingsEntity.getInstance();
    private final DatabaseStorageStrategyVisita visitaDatabase = new DatabaseStorageStrategyVisita();
    private final ListaVisite listaVisite = ListaVisite.getIstanzaListaVisite();
    private final FileManagerVisite visitaFileManager = new FileManagerVisite(); // Gestione file system
    private final PrenotazioneViewControllerGrafico controllerGraficoPrenotazione;
    public PrenotazioneVisitaControllerApp(PrenotazioneVisitaView view) {
        this.controllerGraficoPrenotazione = new PrenotazioneViewControllerGrafico(view);
    }
    public void salvaPrenotazione(PrenotazioneVisitaBean bean) {
        Visita visita = PrenotazioneVisitaBean.toVisita(bean);
        int storageOption = config.getStorageOption(); // Ottieni l'opzione di salvataggio
        switch (storageOption) {
            case 0: // Salvataggio in memoria RAM (Liste)
                listaVisite.aggiungiVisita(visita);
                break;
            case 1: // Salvataggio su Database
                if (!visitaDatabase.salva(visita)) {
                    throw new IllegalArgumentException("Salvataggio database fallito");
                }
                break;
            case 2: // Salvataggio su File System
                if (!visitaFileManager.salva(visita)) {
                    throw new IllegalArgumentException("Salvataggio nel file system fallito");
                }
                break;
            default:
                throw new IllegalArgumentException("Opzione di salvataggio non valida: " + storageOption);
        }
    }
    protected void isValidInputPrenotazione(PrenotazioneVisitaBean bean) {
        if (validatePrenotazione(bean)) {
            salvaPrenotazione(bean);
        }
    }
    private boolean validatePrenotazione(PrenotazioneVisitaBean bean) {
        return controllerGraficoPrenotazione.isValidDataVisita(bean)
                && controllerGraficoPrenotazione.isValidOrarioVisita(bean)
                && controllerGraficoPrenotazione.isValidSpecialista(bean)
                && controllerGraficoPrenotazione.isValidTipoVisita(bean)
                && controllerGraficoPrenotazione.isValidMotivoVisita(bean);
    }
}