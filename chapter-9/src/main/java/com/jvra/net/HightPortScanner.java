package com.jvra.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by jansel on 07/08/14.
 */
public class HightPortScanner implements PortScanner{
    @Override
    public void scan() {
        InetAddress localhost = null;
        try {
            localhost = InetAddress.getLocalHost();
        }catch ( UnknownHostException ex ){
            ex.printStackTrace();
        }
        if( null != localhost ){
            for( int i=1023;i<65536; ++i ){
                try{
                    Socket socket = new Socket( localhost,i );
                    System.err.println( "There are server listening on port "+i );
                }catch( IOException ex) {

                }
            }
        }
    }
}
