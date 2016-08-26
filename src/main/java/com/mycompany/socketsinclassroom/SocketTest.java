package com.mycompany.socketsinclassroom;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Henrik
 */
public class SocketTest {

    static String ip = "localhost";
    static int portNum = 8080;

    public static void handleClient(Socket s) {
        try {

            Scanner scan = new Scanner(s.getInputStream());
            PrintWriter prnt = new PrintWriter(s.getOutputStream(), true);
            String msg = "";
            while (!msg.equals("STOP")) {
                
                msg = scan.nextLine();

                if (msg.contains("UPPER#")) 
                {
                    prnt.println(msg.toUpperCase().substring(6));
                } 
                else if (msg.contains("LOWER#")) 
                {
                    prnt.println(msg.toLowerCase().substring(6));
                } 
                else if (msg.contains("REVERSE#")) 
                {
                    
                    String reverse = new StringBuffer(msg.substring(8)).
                    reverse().toString();
                    
                    prnt.println(reverse);
                } 
                else if (msg.contains("TRANSLATE#")) 
                {
                    String word = msg.toLowerCase().substring(10);
                    
                    if(word.contains("dog")||word.contains("Dog")||word.contains("DOG"))
                        prnt.println("Hund");
                    else
                        prnt.println("#NOT_FOUND");
                }
                else 
                {
                   prnt.println("No command. Closing connection.");
                   s.close();
                }
             

            }
            scan.close();
            prnt.close();
            s.close();

        } catch (IOException ex) {
            Logger.getLogger(SocketTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws IOException {

        if (args.length == 2) {
            ip = args[0];
            portNum = Integer.parseInt(args[1]);
        }

        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(ip, portNum));
        System.out.println("server started and listening on: " + portNum + " bound to ip: " + ip);

        while (true) {
            Socket link = ss.accept();
            System.out.println("New client connection.");
            handleClient(link);
        }

    }

}
