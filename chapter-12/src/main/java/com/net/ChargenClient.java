package com.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * Created by Jansel Valentin R. (jrodr) on 08/20/14.
 */
public class ChargenClient {

    public static void main( String...args ) throws IOException{
        SocketChannel channel = SocketChannel.open();
        channel.connect( new InetSocketAddress(InetAddress.getLocalHost(),1999));

        if( channel.isConnected() ){
            ByteBuffer buffer = ByteBuffer.allocate(74);
            channel.read( buffer );

            buffer.flip();
            if( buffer.hasRemaining() ){
                Charset charset = Charset.defaultCharset();
                System.out.println( charset.decode(buffer));
            }
            buffer.clear();
            channel.close();
        }
    }
}
