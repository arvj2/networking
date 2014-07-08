package com.jvra.net;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Created by Jansel R. Abreu (Vanwolf) on 7/1/2014.
 */
public class Weblog {

    public void translate(String file) throws IOException {
        translate(new File(file));
    }

    public void translate(File file) throws IOException {
        translate(new FileInputStream(file));
    }

    public void translate(InputStream in) throws IOException {
        if (null == in)
            return;
        Date start = new Date();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {
            String line;
            while (null != (line = reader.readLine())) {
                int lastIndex = line.indexOf(' ', 0);
                String ip = line.substring(0, lastIndex);
                String theRest = line.substring(lastIndex, line.length());

                try {
                    InetAddress inet = InetAddress.getByName(ip);
                    System.out.println(inet.getHostName() + " " + theRest);
                } catch (UnknownHostException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Date end = new Date();
        double elapsed = (end.getTime() - start.getTime()) / 1E3;
        System.out.println("Elapsed time " + elapsed + " seconds");
    }
}
