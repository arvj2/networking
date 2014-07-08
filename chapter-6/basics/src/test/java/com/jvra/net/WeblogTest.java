package com.jvra.net;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by Jansel R. Abreu (Vanwolf) on 7/1/2014.
 */
public class WeblogTest {

    private Weblog logger;

    @BeforeTest
    public void setup() {
        logger = new Weblog();
    }

    @Test
    public void testTranslationToHostName() {
        try {
            logger.translate("C:/nio/server log.txt");
        }catch ( Exception ex ){
            throw new RuntimeException(ex);
        }
    }
}
