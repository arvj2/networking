package com.jvra.net;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by Jansel R. Abreu (Vanwolf) on 7/1/2014.
 */
public class LookupWorker implements Runnable {
    private List<String> entries;
    private PooledWeblog logger;


    public LookupWorker(PooledWeblog logger, List<String> entries) {
        this.entries = entries;
        this.logger = logger;
    }

    @Override
    public void run() {
        for (; ; ) {
            String line = null;
            synchronized (entries) {
                if (logger.isFinished())
                    return;
                try {
                    entries.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if( 0< entries.size() )
                    line = entries.remove(entries.size() - 1);
            }
            if( null == line )
                continue;

            int lastIndex = line.indexOf(' ', 0);
            String ip = line.substring(0, lastIndex);
            String host = ip;
            try{
                InetAddress inet = InetAddress.getByName(ip);
                host  = inet.getHostName();
            }catch ( UnknownHostException ex ){
                ex.printStackTrace();
            }

            String theRest = line.substring(lastIndex, line.length());

            try {
                logger.log(host + " " + theRest);
            }catch ( Exception e ){
                e.printStackTrace();
            }
        }
    }
}
