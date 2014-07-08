package com.jvra.net;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;

/**
 * Created by Jansel R. Abreu (Vanwolf) on 7/1/2014.
 */
public class PooledWeblogTest {
    private PooledWeblog logger;

    @BeforeTest
    public void setup(){
        try {
            logger = new PooledWeblog(new FileInputStream("C:/nio/server log.txt"), System.out, 100);
        }catch ( Exception ex ){
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void testTranslateToHostAddress(){
        Assert.assertNotNull(logger);
        logger.processLog();
    }
}
