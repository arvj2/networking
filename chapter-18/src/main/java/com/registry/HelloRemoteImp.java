package com.registry;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Jansel Valentin R. (jrodr) on 08/23/14.
 */
public class HelloRemoteImp extends UnicastRemoteObject implements HelloRemote{

    public HelloRemoteImp() throws RemoteException {
    }

    @Override
    public String say() {
        return null;
    }
}
