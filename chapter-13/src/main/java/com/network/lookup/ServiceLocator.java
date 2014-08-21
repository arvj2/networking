package com.network.lookup;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.net.*;

/**
 * Created by Jansel Valentin R. (jrodr) on 08/21/14.
 */
public class ServiceLocator {

    private static final int SERVICES_PORT = 13010;
    private int bufferSize;
    private int timeout;

    private DatagramPacket outgoing;
    private DatagramSocket socket;

    public ServiceLocator(int bufferSize, int timeout ) throws SocketException, UnknownHostException{
        this.bufferSize = bufferSize;
        this.timeout = timeout;

        socket = new DatagramSocket(0);
        socket.connect(InetAddress.getLocalHost(),SERVICES_PORT );
    }

    public ObjectName lookup( ObjectName service ) throws MalformedObjectNameException,UnknownHostException{
        String name = service.getCanonicalName();
        System.out.println( "Looking for service "+name+"..." );

        byte[] data = name.getBytes();
        outgoing = new DatagramPacket(data,0,data.length,InetAddress.getLocalHost(),SERVICES_PORT);
        return null;
    }

    public static void main( String...args) throws  UnknownHostException{
        InetAddress.getByName( "224.0.0.125" );
    }
}
