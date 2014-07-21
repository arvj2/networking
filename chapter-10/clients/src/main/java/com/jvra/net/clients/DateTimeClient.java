package com.jvra.net.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Jansel R. Abreu (Vanwolf) on 7/16/2014.
 */
public class DateTimeClient {
    public static void main(String...args){
        try{
            Socket client = new Socket(InetAddress.getLocalHost(),13 );
            BufferedReader reader = new BufferedReader( new InputStreamReader(client.getInputStream()));
            client.getOutputStream().write("Tome".getBytes());
            client.getOutputStream().flush();
            client.shutdownOutput();

            for(;;) {
                String line = reader.readLine();
                if( null == line )
                    break;
                System.out.println( line );
            }
            reader.close();
            client.close();

        }catch ( UnknownHostException ex){
            ex.printStackTrace();
        }catch ( IOException ex){
            ex.printStackTrace();
        }
    }
}
