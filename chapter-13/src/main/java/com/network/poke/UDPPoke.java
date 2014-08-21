package com.network.poke;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;

/**
 * Created by Jansel Valentin R. (jrodr) on 08/21/14.
 */
public class UDPPoke {

    private int bufferSize;
    private DatagramSocket socket;
    private DatagramPacket outgoing;

    public UDPPoke(InetAddress host, int port, int bufferSize, int timeout) throws SocketException {
        outgoing = new DatagramPacket(new byte[1], 1, host, port);
        this.bufferSize = bufferSize;

        socket = new DatagramSocket(0);
        socket.connect(host, port);
        socket.setSoTimeout(timeout);
    }

    public UDPPoke(InetAddress host, int port, int bufferSize) throws SocketException {
        this(host, port, bufferSize, 30000);
    }

    public UDPPoke(InetAddress host, int port) throws SocketException {
        this(host, port, 8192, 30000);
    }


    public byte[] poke() {
        byte[] response = null;
        try {
            socket.send(outgoing);
            DatagramPacket incoming = new DatagramPacket(new byte[bufferSize], bufferSize);
            socket.receive(incoming);

            int numBytes = incoming.getLength();
            response = new byte[numBytes];
            System.arraycopy(incoming.getData(), 0, response, 0, numBytes);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }


    public static void main(String... args) {

        InetAddress host = null;
        int port = 0;

        try {
            host = InetAddress.getByName(args[0]);
            port = Integer.valueOf(args[1]);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        String data = null;
        byte[] response = null;

        try {
            UDPPoke poker = new UDPPoke(host, port);
            response = poker.poke();
            data = new String(response, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            try {
                data = new String(response, "8859_1");
            }catch ( Exception e ){}
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        System.out.println( data );
    }
}
