package com.jvra.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by jansel on 07/08/14.
 */
public class LowPortScanner implements PortScanner{
    @Override
    public void scan() {

            for( int i=0;i<=1024; ++i ){
                try{
                    Socket socket = new Socket( "localhost",i );
                    System.err.println( "There are server listening on port "+i );
                }catch( IOException ex) {

                }
            }
    }
}
