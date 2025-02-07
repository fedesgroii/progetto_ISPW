package startupconfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AvviaMySQL {
    public static void main(String[] args) {
        String comando = "sudo /usr/local/mysql/support-files/mysql.server start";
        eseguiComando(comando);
    }

    public static void eseguiComando(String comando) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", comando);
            processBuilder.redirectErrorStream(true); // Unisce stdout e stderr
            Process process = processBuilder.start();

            // Legge l'output del comando
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Attende la terminazione del processo
            int exitCode = process.waitFor();
            System.out.println("Processo terminato con codice: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
