package com.jvra.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Jansel R. Abreu (Vanwolf) on 7/15/2014.
 */
public class EchoClient {
    public static void main(String... args) {
        String host = "localhost";

        if (0 < args.length)
            host = args[0];

        PrintWriter writer = null;
        BufferedReader reader = null;
        try {
            Socket socket = new Socket(host, 7);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedReader userReader = new BufferedReader(new InputStreamReader(System.in));
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("Connected to echo server");

            while (true) {
                String line = userReader.readLine();
                if (".".equals(line)) break;
                writer.println(line);
                writer.flush();

                System.out.println(reader.read());
            }
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
