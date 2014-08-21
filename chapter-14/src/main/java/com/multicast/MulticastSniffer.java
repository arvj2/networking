package com.multicast;

import java.io.IOException;
import java.net.*;

/**
 * Created by Jansel Valentin R. (jrodr) on 08/21/14.
 */
public class MulticastSniffer {

    public static void main(String... args) {
        InetAddress group = null;
        int port = 0;

        try {
            group = InetAddress.getByName(args[0]);
            port = Integer.parseInt(args[1]);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println( "Usage: MulticastSniffer multicast_address port" );
        }

        MulticastSocket socket = null;
        try{
            socket = new MulticastSocket(port);
            socket.joinGroup(group);

            byte[] buffer = new byte[8192];
            DatagramPacket packet = new DatagramPacket(buffer,buffer.length);

            System.out.println( "Sniffer on "+socket.getLocalAddress()+" : "+socket.getLocalPort()+" : "+socket.getPort()+" : "+socket.getInetAddress() );
            for(;;) {
                socket.receive(packet);
                String received = new String(packet.getData());
                System.out.println(received);
            }
        }catch( SocketException ex ){
            ex.printStackTrace();
        }catch( IOException ex ){
            ex.printStackTrace();
        }finally {
            try {
                socket.leaveGroup(group);
            }catch ( IOException ex) {}
            socket.close();
        }
    }
}
