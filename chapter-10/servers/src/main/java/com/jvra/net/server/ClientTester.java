package com.jvra.net.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;

/**
 * Created by Jansel R. Abreu (Vanwolf) on 7/17/2014.
 */
public class ClientTester {
    public static void main(String... args) {
        try {
            ServerSocket server = new ServerSocket(1136, 1);
            System.out.println("Server listening on port " + server.getLocalPort() + "...\n");

            for (; ; ) {
                final Socket client = server.accept();
                try {
                    final Thread input = new Thread(new InputWorker(client.getInputStream()));
                    final Thread output = new Thread(new OutputWorker(client.getOutputStream()));
                    input.start();
                    output.start();

                    try {
                        input.join();
                        output.join();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private static class InputWorker implements Runnable {
        private InputStream in;

        private InputWorker(InputStream in) {
            this.in = in;
        }

        @Override
        public void run() {
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            ReadableByteChannel channel = Channels.newChannel(in);

            StringBuilder builder = new StringBuilder();
            try {
                for (; ; ) {
                    int i = channel.read(buffer);
                    if (0 >= i)
                        break;
                    buffer.flip();
                    builder.append(Charset.forName("UTF-8").decode(buffer));
                    buffer.clear();
                    System.out.println(builder.toString());
                    builder.setLength(0);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (null != channel) {
                    try {
                        System.out.println( "Closing input stream for server socket" );
                        channel.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }


    private static class OutputWorker implements Runnable {
        private OutputStream out;

        private OutputWorker(OutputStream out) {
            this.out = out;
        }

        @Override
        public void run() {
            Writer writer = new OutputStreamWriter(out);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                for (; ; ) {
                    String line = reader.readLine();
                    if (".".equals(line))
                        break;
                    writer.write(line);
                    writer.flush();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (null != writer) {
                    try {
                        System.out.println( "Closing output stream for server socket" );
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}
