package com.jvra.net.server;

import java.io.*;
import java.net.*;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jansel R. Abreu (Vanwolf) on 7/16/2014.
 */
public final class DateTimeServer {

    public static void main(String... args) {
        final ExecutorService exec = Executors.newFixedThreadPool(5);
        try {
            final ServerSocket server = new ServerSocket(13);
            for (; ; ) {
                try {
                    final Socket client = server.accept();
                    exec.execute(new Runnable() {
                        @Override
                        public void run() {
                            final Socket kClient = client;
                            Date date = Calendar.getInstance().getTime();
                            try {
                                BufferedReader reader = new BufferedReader( new InputStreamReader(kClient.getInputStream()));
                                for(;;) {
                                    String line = reader.readLine();
                                    if( null == line )
                                        break;
                                    System.out.println( line );
                                }

                                Writer writer = new BufferedWriter( new PrintWriter(kClient.getOutputStream()) );
                                writer.write( date.toString()+"\r\n" );
                                writer.flush();
                                writer.close();

                            }catch ( IOException ex ){
                                ex.printStackTrace();
                            }finally{
                                try {
                                    kClient.close();
                                }catch ( IOException ex ){
                                    ex.printStackTrace();
                                }
                            }
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
