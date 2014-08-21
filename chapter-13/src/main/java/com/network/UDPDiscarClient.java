package com.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by Jansel Valentin R. (jrodr) on 08/21/14.
 */
public class UDPDiscarClient {
    public static void main( String...args ){
        try {
            DatagramSocket socket = new DatagramSocket();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

//            socket.connect(InetAddress.getLocalHost(),1999);
            for(;;){
                String line = null;
                try{
                    line = reader.readLine();
                }catch ( IOException ex ){
                    ex.printStackTrace();
                    continue;
                }

                if( ".".equals(line) )
                    break;

                byte[] data = line.getBytes();
                DatagramPacket packet = new DatagramPacket(data,0,data.length,InetAddress.getLocalHost(),1999);
                try {
                    socket.send(packet);
                    System.out.println( "Connected to "+socket.getInetAddress()+" : "+socket.getLocalAddress()+" : "+socket.getPort()+" : "+socket.getLocalPort() );
                }catch ( IOException ex){
                    ex.printStackTrace();
                }

            }
        }catch ( Exception ex ){
            ex.printStackTrace();
        }
    }
}
