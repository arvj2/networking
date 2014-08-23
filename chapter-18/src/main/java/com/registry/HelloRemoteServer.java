package com.registry;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Jansel Valentin R. (jrodr) on 08/234/14.
 */
public class HelloRemoteServer {
    public static void main( String...args ){

        try{
            HelloRemoteImp imp = new HelloRemoteImp();
            Naming.rebind( "hello", UnicastRemoteObject.exportObject(imp));
            System.out.println( "Hello server is ready" );
        }catch ( RemoteException ex ){
            ex.printStackTrace();
        }
        catch ( MalformedURLException ex) {
            ex.printStackTrace();
        }
    }
}
