
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * █║▌│ █│║▌ ║││█║▌ │║║█║ 
 * Author : Regør [★] 
 * Who in Black Byte 
 * Program Tombola Carrera Camells V.1 
 * DataTime 16/1/2015 23:00
 */
class CArbitre {

    private int numJugadors;
    private int torn;
    private boolean jugant;
    private int distancia;

    public CArbitre(int jugadors) {
        this.jugant = true;
        this.numJugadors = jugadors;
        this.torn = 1;
        this.distancia = 15; //cRandom.generaInt(10);
    }

    public synchronized boolean getFi() {
        return jugant;
    }

    public synchronized boolean compTorn(int identificador) {
        boolean etToca;
        while (identificador != torn) {
            try {
                //print per veure com els jugadors esperen el torn
                //System.out.println("EL jugador "+identificador+" esta esperant");
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(CArbitre.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        notifyAll();
        if (identificador == torn) {
            etToca = true;
        } else {
            etToca = false;
        }
        return etToca;
    }

    public synchronized int compNum(int valor) {
        int winner = 0;
        if (valor >= distancia) {
            jugant = false;
            System.out.println("El jugador " + torn + " guanya");
            winner = torn;
            torn = 666;
        } else {
            System.out.println("El jugador " + torn +":"+ valor +" / 250");
            if (torn >= numJugadors) {
                torn = 0;
            }
        }
        torn++;
        notifyAll();
        return winner;
    }
}
