
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * █║▌│ █│║▌ ║││█║▌ │║║█║ Author : Regør [★] Who in Black Byte Program Tombola
 * Carrera Camells V.1 DataTime 16/1/2015 23:00
 */
public class CPartidaThread extends Thread {

    CPartidaThread(Socket conexio1, Socket conexio2, Socket conexio3, CPrincipal aThis) {
        ArrayList<Socket> conexions = new ArrayList<>();
        conexions.add(conexio1);
        conexions.add(conexio2);
        conexions.add(conexio3);
        boolean done = true;
        int MAX_CAMELLS = 3;
        try {
            CCamell[] camells = new CCamell[MAX_CAMELLS];
            CArbitre arbitre = new CArbitre(MAX_CAMELLS);
            for (int x = 0; x < MAX_CAMELLS; x++) {
                camells[x] = new CCamell(x + 1, arbitre, conexions.get(x));
            }
            for (int x = 0; x < MAX_CAMELLS; x++) {
                camells[x].start();
            }
            while (done) {
                if (!arbitre.getFi()) {
                    done = false;
                }
            }
            conexions.stream().forEach((connec) -> {
                try {
                    connec.close();
                } catch (IOException ex) {
                    System.out.println("Error E/S");
                }
            });

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.exit(0);
    }

}
