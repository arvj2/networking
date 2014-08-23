package com.registry;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Jansel Valentin R. (jrodr) on 08/23/14.
 */
public abstract interface HelloRemote extends Remote{
    public abstract  String say() throws RemoteException;
}
