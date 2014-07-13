package com.jvra.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by jansel on 07/13/14.
 */
public class Basic {
    public static void main(String...args){
        try{
            Socket socket = new Socket(InetAddress.getByName("time.nist.gov"),13);
            InputStream in = socket.getInputStream();
            StringBuffer buffer = new StringBuffer();
            int i;
            for(;;){
                i= in.read();
                if(0>=i)
                    break;
                buffer.append((char)i);
            }
            System.out.println( buffer.toString().trim() );
            buffer.setLength(0);

        }catch( UnknownHostException ex ){
            ex.printStackTrace();
        }catch( IOException ex){
            ex.printStackTrace();
        }
    }
}
