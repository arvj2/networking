package com.jvra.net;

import java.net.Authenticator;

/**
 * Created by Jansel R. Abreu (Vanwolf) on 7/8/2014.
 */
public class SecureSourceViewer {
    static{
        Authenticator.setDefault(new DialogAuthenticator());
    }
    public static void main(String...args){

    }
}
