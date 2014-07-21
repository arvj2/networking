package com.jvra.sm.game;

import java.io.*;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Hashtable;

/**
 * Created by Jansel R. Abreu (Vanwolf) on 7/21/2014.
 */
public final class HightScore {

    private String game;
    private File hightscore;

    public HightScore(String game) {
        this.game = game;
        hightscore = AccessController.doPrivileged(new PrivilegedAction<File>() {
            @Override
            public File run() {
                return new File( System.getProperty("user.home") + File.separator + ".highscore" );
            }
        });
    }

    public void setScore(final Integer i) throws IOException{
        SecurityManager sm = System.getSecurityManager();
        if( null != sm )
            sm.checkPermission( new HightScorePermission(game) );
        try{
                AccessController.doPrivileged( new PrivilegedExceptionAction<Object>() {
                    @Override
                    public Object run() throws Exception {
                        Hashtable scores;
                        FileInputStream in = new FileInputStream( hightscore );
                        ObjectInputStream oin = new ObjectInputStream( in );

                        scores = ( Hashtable )oin.readObject();

                        if( null == scores )
                            scores = new Hashtable(13);
                        scores.put( game,new Integer(i));

                        ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream(hightscore) );
                        out.writeObject( scores );
                        out.flush();
                        out.close();
                        return null;
                    }
                });
        }catch ( PrivilegedActionException ex ){
            ex.printStackTrace();
        }
    }

    public Integer getScore() {
        SecurityManager sm = System.getSecurityManager();
        if( null != sm )
            sm.checkPermission( new HightScorePermission(game) );

        Integer score = null;
        try {
            score = AccessController.doPrivileged(new PrivilegedExceptionAction<Integer>() {
                @Override
                public Integer run() throws Exception {
                    Hashtable scores;
                    FileInputStream in = new FileInputStream( hightscore );
                    ObjectInputStream oin = new ObjectInputStream( in );

                    scores = ( Hashtable )oin.readObject();

                    if( null == scores )
                        scores = new Hashtable(13);

                    return (Integer)scores.get(game);
                }
            });
        }catch ( PrivilegedActionException ex ){
            ex.printStackTrace();
        }

        return null == score ? -1 : score;
    }
}
