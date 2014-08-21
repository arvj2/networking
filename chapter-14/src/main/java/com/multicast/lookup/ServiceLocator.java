package com.multicast.lookup;

import javax.management.ObjectName;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Jansel Valentin R. (jrodr) on 08/21/14.
 */
public class ServiceLocator {

    private InetAddress reception; // 225.1.2.127   // Grupo en el que estaran todos los multicast locators
    private InetAddress mcast;     // 225.1.2.129   // Grupo de todos los servicios
    private int rport;             // 5555          // Puerto al que deberea ser reportada la respuesta al locator
    private int mport;             // 5558          // Puerto en que estaran todos los servicios

    public ServiceLocator(InetAddress reception, InetAddress mcast, int rport, int mport) {
        this.reception = reception;
        this.mcast = mcast;
        this.rport = rport;
        this.mport = mport;
    }

    public ObjectName lookup(String service) throws Exception {

        /**
         * Se prepara el socket para unirlo al grupo de locators, esta facultad permite
         * que si se quiere tener otros multicast sockets escuchando la respuesta de los
         * servicios,  tambien este habilitado
         */
        MulticastSocket socket = new MulticastSocket(rport);
        socket.joinGroup(reception);
        /**
         * Se espera que una vez deliberada la solicitud a los servicios su respuesta no exeda de 15 seg
         */
        socket.setSoTimeout(15000);

        Future<ObjectName> get = receive(socket);

        socket.setTimeToLive(1);
        byte[] data = service.getBytes();
        /**
         * Este packet va dirigido al group de los servicios, pero al llegar a los servicios, estos podran obtener la informacion del
         * locator para devolverle a este, asi se asegura que si la direccion a la que esta
         * atado el locator es un MulticastSocket o un DatagramSocket el mensaje enviado por los
         * servicios llegue al Locator
         */
        DatagramPacket packet = new DatagramPacket(data, data.length, mcast, mport);
        socket.send(packet);

        return get.get();
    }


    private Future<ObjectName> receive(final MulticastSocket socket) {
        ExecutorService exec = Executors.newCachedThreadPool();
        Future<ObjectName> get = exec.submit(new Callable<ObjectName>() {
            @Override
            public ObjectName call() throws Exception {

                DatagramPacket packet = new DatagramPacket(new byte[8192], 8192);
                socket.receive(packet);

                System.out.println("Packet come from " + packet.getAddress() + " on port " + packet.getPort());
                ObjectName name = null;
                try {
                    String service = new String(packet.getData());
                    name = new ObjectName(service);
                } catch (Exception ex) {
                }
                return name;
            }
        });
        exec.shutdown();
        return get;
    }


    public static void main(String... args) throws Exception {
        InetAddress reception = null;
        InetAddress mcast = null;
        int rport = 0;
        int mport = 0;

        try {
            reception = InetAddress.getByName(args[0]);
            rport = Integer.valueOf(args[1]);

            mcast = InetAddress.getByName(args[2]);
            mport = Integer.valueOf(args[3]);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            ServiceLocator locator = new ServiceLocator(reception, mcast, rport, mport);
            System.out.println("Solved by " + locator.lookup(String.valueOf(args[4])));
        } catch (Exception ex) {
            System.out.println("No provider for this request");
        }
//
//        ObjectName o = ObjectName.getInstance( "com.*:provider=oracle" );
//        ObjectName o2 = ObjectName.getInstance( "com.jvra.test:provider=oracle" );
//        System.out.println( o.apply(o2) );

    }
}
