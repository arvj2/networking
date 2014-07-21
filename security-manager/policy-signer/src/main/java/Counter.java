import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jansel R. Abreu (Vanwolf) on 7/21/2014.
 */
public class Counter {

    private static void count( String filename ){
        int count=0;
        try{
            InputStream in = new FileInputStream( new File( filename ) );
            while( in.read() >0 )
                count++;
        }catch ( IOException ex ){
            ex.printStackTrace();
        }
        System.out.println( "\n"+count+" words.!\n" );
    }


    public static void main( String...args ){
        count( "C:/nio/fm/fm" );
    }
}
