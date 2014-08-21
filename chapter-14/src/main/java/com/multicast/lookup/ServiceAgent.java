package com.multicast.lookup;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by Jansel Valentin R. (jrodr) on 08/21/14.
 */
public class ServiceAgent {
    private InetAddress mcast;
    private int mport;
    private ObjectName id;


    public ServiceAgent(InetAddress mcast, int mport, String id ) throws MalformedObjectNameException{
        this.mcast = mcast;
        this.mport = mport;

        this.id = new ObjectName(id);
    }

    public void listen() throws Exception{
        MulticastSocket socket = new MulticastSocket(mport);
        socket.joinGroup(mcast);

        DatagramPacket packet = new DatagramPacket(new byte[8192],8192);
        for(;;) {
            socket.receive(packet);

            String service = new String(packet.getData(),0,packet.getLength());
            ObjectName name = ObjectName.getInstance(service);

            if (name.apply(id)) {
                System.out.println( "Responding to "+packet.getAddress()+" at port "+packet.getPort() );

                byte[] data = id.toString().getBytes();
                DatagramPacket response = new DatagramPacket(data,data.length,packet.getAddress(),packet.getPort());
                socket.setTimeToLive(1);
                socket.send(response);
            }else{
                System.out.println( "Discarding "+name+" self "+id );
            }
        }
    }

    public static void main( String...args ) throws Exception{
        InetAddress mcast = null;
        int mport =0;
        String id = null;

        try{
            mcast = InetAddress.getByName( args[0] );
            mport = Integer.valueOf( args[1] );
            id = String.valueOf(args[2]);
        }catch ( Exception ex ){
            ex.printStackTrace();
        }

        try{
            new ServiceAgent(mcast,mport,id).listen();
        }catch ( Exception ex) {
            ex.printStackTrace();
        }

    }
}
