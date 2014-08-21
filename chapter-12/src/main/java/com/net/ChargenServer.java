package com.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Jansel Valentin R. (jrodr) on 08/20/14.
 */
public class ChargenServer {
    public static void main( String...args ){
        byte[] rotation = new byte[95*2];
        for( byte i =' ';i<'~';++i ){
            rotation[ i-' '] = i;
            rotation[ i+95 - ' '] = i;
        }

        Selector selector = null;
        try {
            selector = SelectorProvider.provider().openSelector();
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind( new InetSocketAddress(1999));

            server.register(selector, SelectionKey.OP_ACCEPT);
        }catch ( IOException ex ){
            ex.printStackTrace();
        }

        for(;;){
            try{
                selector.select();
            }catch ( IOException ex ){
                ex.printStackTrace();
                return;
            }

            Set<SelectionKey> selected = selector.selectedKeys();
            Iterator<SelectionKey> it = selected.iterator();

            while( it.hasNext() ){
                SelectionKey key = it.next();
                it.remove();

                try{

                    if( key.isAcceptable() ){
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();

                        SocketChannel client = server.accept();

                        System.out.println( "Accepting connection from "+client.getRemoteAddress()+" to "+client.getLocalAddress() );
                        client.configureBlocking(false);

                        ByteBuffer attachment = ByteBuffer.allocate(74);
                        attachment.put( rotation,0,72 );
                        attachment.put((byte)'\r' );
                        attachment.put((byte)'\n' );
                        attachment.flip();

                        client.register(selector,SelectionKey.OP_WRITE,attachment);
                    }else if( key.isWritable() ){
                        SocketChannel client = (SocketChannel) key.channel();

                        ByteBuffer attachment = (ByteBuffer) key.attachment();
                        if( attachment.hasRemaining() ) {
                            attachment.rewind();

                            int first = attachment.get();
                            attachment.rewind();

                            int position = first - ' ' + 1;
                            attachment.put(rotation, position, 72);
                            attachment.put((byte)'\r' );
                            attachment.put((byte)'\n' );
                            attachment.flip();
                        }
                        client.write(attachment);
                    }

                }catch ( IOException ex ){
                    key.cancel();
                    try {
                        key.channel().close();
                    }catch ( Exception e ){}

                    ex.printStackTrace();
                }
            }
        }
    }
}
