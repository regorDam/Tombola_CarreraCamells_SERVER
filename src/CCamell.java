
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * █║▌│ █│║▌ ║││█║▌ │║║█║ 
 * Author Regør [★] Who in Black Byte 
 * Program Tombola carreraCamells V.1
 */
class CCamell extends Thread {

    private int id;
    private int num;
    private int distancia;
    private int guanyador;
    private CArbitre arbitre;
    private Socket endoll;
    private ObjectOutputStream sortida;
    private ObjectInputStream entrada;
    boolean done = false;

    public CCamell(int identificador, CArbitre a, Socket socket) {
        this.id = identificador;
        this.arbitre = a;
        this.endoll = socket;
        this.distancia = 0;
    }

    public void run() {
        obtenirFlux(endoll);
        while (arbitre.getFi()) {
            if (arbitre.compTorn(id)) {
                try {
                    String frase = "Apreta una tecla per tirar el dau, Soc el camell " + id;
                    
                    enviarDades(frase, sortida);
                    obtenirFlux(endoll);
                    entrada.readObject(); //IM HERE
                    num = cRandom.generaInt(1,10);
                    distancia = distancia + num;
                    //enviarDades("distancia: "+num+" / 250", sortida);
                    guanyador = arbitre.compNum(distancia);
                } catch (IOException ex) {
                    System.err.println("Error E/S "+ex.toString());
                    break;
                } catch (ClassNotFoundException ex) {
                    System.err.println("Error class not found");
                    break;
                }
            } else {
                arbitre.compTorn(id);
            }
        }
        enviarDades("ByeBye El guanyador es:"+guanyador, sortida);

    }

    public void safeStop() {
        done = true;
    }

    private void enviarDades(String msg, ObjectOutputStream desti) {
        try {
            desti.writeObject(msg);
            desti.flush();
        } catch (IOException ex) {
            System.err.println("error d'escritura d'objecte");
        }

    }

    private void obtenirFlux(Socket conexio) {
        try {
            sortida = new ObjectOutputStream(conexio.getOutputStream());
            sortida.flush();
            entrada = new ObjectInputStream(conexio.getInputStream());

        } catch (Exception ex) {
            System.err.println("error al obtenir el flux");
        }
    }
}
