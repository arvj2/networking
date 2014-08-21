package com.multicast;

import java.io.IOException;
import java.net.*;

/**
 * Created by Jansel Valentin R. (jrodr) on 08/21/14.
 */
public class MulticastSender {
    public  static void main( String...args ){

        InetAddress group = null;
        int port = 0;

        byte ttl = (byte)1;

        try{
            group = InetAddress.getByName(args[0]);
            port = Integer.valueOf( args[1] );
        }catch ( Exception ex) {
            ex.printStackTrace();
        }

        byte[] data = "Here's some multicast data\r\n".getBytes();
        DatagramPacket packet = new DatagramPacket(data,data.length,group,port);

        try{
            /**
             * Descomentar si se quiere probar con MulticastSocket en vez de DatagramSocket
             */
//            final MulticastSocket socket = new MulticastSocket();

            DatagramSocket socket = new DatagramSocket();
            /**
             * Si quiero que este sender tambien este subscrito al mismo grupo que reporta
             */
//            final MulticastSocket socket = new MulticastSocket(port);
            /**
             * Si se quiere probar con MulticastSocket en vez de DatagramSocket
             */
//            socket.setTimeToLive(ttl);

            /**
             * Si quiero que este sender tambien este subscrito al mismo grupo que reporta
             */
//            socket.joinGroup(group);
//            new Thread( new Runnable() {
//                @Override
//                public void run() {
//                    byte[] buffer = new byte[8192];
//                    DatagramPacket p = new DatagramPacket(buffer,buffer.length);
//
//                    System.out.println( "Sender on "+socket.getLocalAddress()+" : "+socket.getLocalPort()+" : "+socket.getPort()+" : "+socket.getInetAddress() );
//                    for(;;) {
//                        try {
//                            socket.receive(p);
//                            String received = new String(p.getData());
//                            System.out.println(received);
//                        }catch ( IOException e ){e.printStackTrace();}
//                    }
//                }
//            }).start();
            System.out.println( "Sender on "+socket.getLocalAddress()+" : "+socket.getLocalPort()+" : "+socket.getPort()+" : "+socket.getInetAddress() );

            for (int i = 0; i <= 10; i++) {
                socket.send( packet );
            }
            socket.send(packet);
        }catch ( SocketException ex ){
            ex.printStackTrace();
        }catch ( IOException ex){
            ex.printStackTrace();
        }
    }
}
