package com.jvra.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by jansel on 07/13/14.
 */
public class Basic {
    public static void main(String... args) {
        try{
            ServerSocket socket = new ServerSocket(11155,1000);
            Socket client = socket.accept();
        }catch ( UnknownHostException ex ){
            ex.printStackTrace();
        }catch ( IOException x ){
            x.printStackTrace();
        }
    }
}
