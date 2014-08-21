package com.net;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;

/**
 * Created by Jansel R. Abreu (Vanwolf) on 08/16/14.
 */
public class Main {
    public static void main(String... args) {
        SocketAddress address = new InetSocketAddress("rama.poly.edu",19);
        try {
            SocketChannel channel = SocketChannel.open(address);
            ByteBuffer buffer = ByteBuffer.allocate(74);

            channel.read( buffer );
        }catch ( UnresolvedAddressException ex ){
            ex.printStackTrace();
        }catch ( IOException ed ){
            ed.printStackTrace();
        }
    }

}