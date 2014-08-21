package com.network;

import java.io.IOException;
import java.net.DatagramSocket;

/**
 * Created by Jansel Valentin R. (jrodr) on 08/21/14.
 */
public class Main {
    public static void main( String...args ){

        for (int i = 1024; i <= 65535; i++) {
            try{
                DatagramSocket socket = new DatagramSocket(i);
                socket.close();
            }catch ( IOException ex ){
                System.out.println( "UDP Port "+i+" is in use" );
            }
        }
    }
}
