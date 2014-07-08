package com.jvra.net;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jansel R. Abreu (Vanwolf) on 7/1/2014.
 */
public class PooledWeblog {

    private InputStream in;
    private OutputStream out;
    private boolean finished;
    private int test;
    private int numberOfThread;
    private List<String> entries;


    public static void main(String...args){
        try {
            PooledWeblog logger = new PooledWeblog(new FileInputStream("C:/nio/server log.txt"), System.out, 100);
            logger.processLog();
        }catch ( Exception ex ){
            throw new RuntimeException(ex);
        }
    }

    public PooledWeblog( InputStream in,OutputStream out, int numThread ){
        this.in = in;
        this.out = out;
        this.numberOfThread = numThread;
        entries = Collections.synchronizedList( new ArrayList<String>() );
    }

    public boolean isFinished() {
        return finished;
    }

    public List<String> getEntries() {
        return entries;
    }

    public int getNumberOfThread() {
        return numberOfThread;
    }

    public void processLog(){
        for( int i=0;numberOfThread>i;++i ){
            new Thread(new LookupWorker(this,entries) ).start();
        }
       try( BufferedReader reader = new BufferedReader(  new InputStreamReader(in)) ){
            String line;
           while( null != ( line = reader.readLine() )){
               if( entries.size() > numberOfThread ){
                   try{
                       TimeUnit.MILLISECONDS.sleep( (long) 1E3/numberOfThread );
                   }catch ( InterruptedException ex ){
                       ex.printStackTrace();
                   }
                   continue;
               }

               synchronized (entries){
                   entries.add(line);
                   entries.notifyAll();
               }
               Thread.yield();
           }
       }catch ( IOException ex ){
           ex.printStackTrace();
       }

        finished = true;
        synchronized (entries){
            entries.notifyAll();
        }
    }


    public void log(String entry) throws IOException{
        out.write( entry.getBytes() );
        out.write( System.getProperty( "line.separator","\r\n" ).getBytes() );
        out.flush();
    }
}
