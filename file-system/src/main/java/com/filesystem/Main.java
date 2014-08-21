package com.filesystem;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardOpenOption.*;


/**
 * Created by Jansel R. Abreu (Vanwolf) on 08/16/14.
 */
public class Main {

    static final String file = "/home/jrodr/Documents/tests/nio/file.txt";

    public static void main(String... args) {
        write();
//        read();
    }


    private static void write() {
        try {
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get(file), WRITE, READ, CREATE);

            final CompletionHandler<Integer, Object> handler = new CompletionHandler<Integer, Object>() {
                @Override
                public void completed(Integer integer, Object o) {
                    System.out.println("Attachment " + o + ", after " + integer + " bytes written");
                    System.out.println("CompletionHandler Thread ID: " + Thread.currentThread().getId());
                }

                @Override
                public void failed(Throwable throwable, Object o) {
                    System.out.println("Attachmet " + o + ": ");
                    throwable.printStackTrace();
                }
            };

            final long amount = "Basic".getBytes().length;

            System.out.println("Main Thread: " + Thread.currentThread().getId());

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put("Basic all".getBytes());
            buffer.flip();

            channel.write(buffer, amount);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private static void read() {
        ExecutorService pool = Executors.newCachedThreadPool();
        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get(file), EnumSet.of(READ),pool)) {

            System.out.println( "Main Thread ID: "+Thread.currentThread().getId() );
            final CompletionHandler<Integer,ByteBuffer> handler = new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer integer, ByteBuffer byteBuffer) {
                    for (int i = 0; i < byteBuffer.limit(); i++) {
                        System.out.println( (char) byteBuffer.get(i) );
                    }
                    System.out.println("");
                    System.out.println( "CompletionHandler Thread ID:"+Thread.currentThread().getId() );
                }
                @Override
                public void failed(Throwable throwable, ByteBuffer byteBuffer) {
                    System.out.println( "Read operation failed" );
                }
            };


            final int bufferCount = 5;
            ByteBuffer buffers[] = new ByteBuffer[bufferCount];
            for (int i = 0; i < bufferCount; i++) {
                buffers[i] = ByteBuffer.allocate(10);
                channel.read(buffers[i],i*10,buffers[i],handler);
            }

            pool.awaitTermination(1, TimeUnit.SECONDS);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
