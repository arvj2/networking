package com.jvra.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Jansel R. Abreu (Vanwolf) on 7/1/2014.
 */
public class HostLookup {

    public static void main(String... args) {
        if (0 < args.length) {
            for (int i = 0; args.length > i; ++i) {
                lookup(args[i]);
            }
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            while (null != (line = reader.readLine()) && !line.equals("exit")) {
                lookup(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void lookup(String host) {
        InetAddress inet = null;
        try{
            inet = InetAddress.getByName(host);
        }catch ( UnknownHostException ex ){
            ex.printStackTrace();
        }
        if( null == inet ){
            System.out.println( "Could, not find address "+host );
            return;
        }

        if( isHostName(host) )
            System.out.println( inet.getHostAddress() );
        else
            System.out.println( inet.getHostName() );
    }

    private static boolean isHostName(String host){
        if(-1 != host.indexOf(':'))
            return false;

        char[] chs = host.toCharArray();
        for( int i=0;chs.length>i;++i ){
            if(!Character.isDigit(chs[i]))
                if( '.'!=chs[i] ) {
                    return true;
                }
        }
        return false;
    }
}
