
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
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
 * █║▌│ █│║▌ ║││█║▌ │║║█║ 
 * Author : Regør [★] 
 * Who in Black Byte 
 * Program Tombola Carrera Camells V.1 
 * DataTime 16/1/2015 23:00
 */
public class CPrincipal {

    private ServerSocket server;
    private final int PORT = 4444;
    private boolean listening = true;
    private ArrayList<Socket> listSocketsEspera = new ArrayList<Socket>();
    private ArrayList<Socket> listSocketsEmparellats = new ArrayList<Socket>();
    private int count = 0;
    private ObjectOutputStream sortida;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new CPrincipal();

    }

    public CPrincipal() {
        System.out.println("Benvingut a la tombola de camells (server-main)");
        conexio();
    }

    private void conexio() {
        try {
            server = new ServerSocket(PORT);
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println(ip.getHostAddress() + " --> escoltant al port: " + PORT);
        } catch (IOException ex) {
            System.err.println("no puc escoltar al port " + PORT);
        }

        while (listening) {
            try {
                System.out.println(listSocketsEspera.size());
                listSocketsEspera.add(server.accept());
                String s = listSocketsEspera.get(listSocketsEspera.size() - 1).getInetAddress().getHostAddress();
                System.out.println("client: " + s + " connectat esperant oponents");
                obtenirFlux(listSocketsEspera.get(listSocketsEspera.size() - 1));
                enviarDades("Esperant jugadors..", sortida);

                if (listSocketsEspera.size() >= 3) {
                    new CPartidaThread(listSocketsEspera.get(0), listSocketsEspera.get(1), listSocketsEspera.get(2), this).start();
                    moveArraySockets();
                }

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }

            count++;
            if (!listening) {
                break;
            }
        }
    }

    private void moveArraySockets() {
        listSocketsEmparellats.add(listSocketsEspera.get(0));
        listSocketsEmparellats.add(listSocketsEspera.get(1));
        listSocketsEmparellats.add(listSocketsEspera.get(2));
        listSocketsEspera.remove(2);
        listSocketsEspera.remove(1);
        listSocketsEspera.remove(0);
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
            //entrada = new ObjectInputStream(conexio.getInputStream());

        } catch (Exception ex) {
            System.err.println("error al obtenir el flux");
        }
    }
}
