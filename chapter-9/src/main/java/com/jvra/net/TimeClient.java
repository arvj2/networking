package com.jvra.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by jansel on 07/13/14.
 */
public class TimeClient {

    public final static int DEFAULT_PORT = 37;
    public final static String DEFAULT_HOST = "time.nist.gov";

    public static void main( String...ags ){
        String host = DEFAULT_HOST;
        int port = DEFAULT_PORT;

        TimeZone timeZone = TimeZone.getTimeZone("GTM");
        Calendar epoch1900 = Calendar.getInstance(timeZone);
        epoch1900.set(1900,1,1,0,0,0);

        Calendar epoch1970 = Calendar.getInstance(timeZone);
        epoch1970.set(1970,1,1,0,0,0);

        long time1900 = epoch1900.getTimeInMillis();
        long time1970 = epoch1970.getTimeInMillis();

        long difference =  time1970 - time1900;
        difference /= 1E3;

        System.out.println( "Difference between epoch "+ difference );

        Date current = Calendar.getInstance().getTime();
        System.out.println( "Time In Millis since 1-1-1970 "+ current.getTime() );

        InputStream in = null;
        try{
            final Socket socket = new Socket(host,port);
            in = socket.getInputStream();
            StringBuilder builder = new StringBuilder();

            long secondsSince1900 = 0;

            for( int i=0;4> i;++i ){
                int x = in.read();
                builder.append( x ).append( " " );
                secondsSince1900 = (secondsSince1900 << 8 ) | x;
            }

            long secondsSince1970 = secondsSince1900 - difference;
            secondsSince1970 = (long) (secondsSince1970 * 1E3);

            Date date = new Date( secondsSince1970 );
            System.out.println( "Time In Millis reported by date "+date.getTime() );

            System.out.println("secondsSince1900 = " + secondsSince1900);
            System.out.println("builder.toString() = " + builder.toString());

        }catch(UnknownHostException ex){
            ex.printStackTrace();
        }catch( IOException ex ){
            ex.printStackTrace();
        }finally{
            if( null != in ) {
                try{
                    in.close();
                }catch( IOException ex ){
                    ex.printStackTrace();
                }
            }
        }
    }
}
