package login_insert;

import javafx.scene.Scene;
import javafx.scene.text.Text;

public class LoginSpecialistControllerGrafico {
    private final LoginSpecialistView view;

    public LoginSpecialistControllerGrafico(LoginSpecialistView view) {
        this.view = view;
    }

    public void showError() {

     // Modifica errorText direttamente tramite il getter
      /*  Text errorText = view.getErrorText();
        errorText.setVisible(true);*/
        view.showError();

        // Alternativa: uso di lookup per trovare errorText tramite ID
       /* Scene scene = view.getScene(); // Ottieni la scena dalla view
        Text errorTextViaLookup = (Text) scene.lookup("#errorText");
        if (errorTextViaLookup != null) {
            errorTextViaLookup.setVisible(true);
        }*/
    }
}
