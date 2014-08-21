package com.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by Jansel Valentin R. (jrodr) on 08/21/14.
 */
public class UDPDiscardServer {

    private static final int MAX_PACKET_SIZE =  2;

    public static void main( String...args ){
        byte[] buffer = new byte[MAX_PACKET_SIZE];

        try {
            DatagramSocket socket = new DatagramSocket(1999);
            DatagramPacket packet = new DatagramPacket(buffer,buffer.length);

            System.out.println( "Running on "+socket.getInetAddress()+" : "+socket.getLocalAddress()+" : "+socket.getPort()+" : "+socket.getLocalPort() );
            while(true){
                try {
                    socket.receive(packet);
                }catch ( IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    String s = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
                    System.out.println( packet.getAddress()+" at port "+packet.getPort()+" says "+s );
                    packet.setLength(buffer.length);
                }catch ( UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }

            }
        }catch ( SocketException ex ){
            ex.printStackTrace();
        }
    }
}


