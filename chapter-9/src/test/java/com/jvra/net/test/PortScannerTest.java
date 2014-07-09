package com.jvra.net.test;

import com.jvra.net.HightPortScanner;
import com.jvra.net.PortScanner;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;


/**
 * Created by jansel on 07/08/14.
 */
public class PortScannerTest {

    private PortScanner portScanner;

    @BeforeTest
    public void setup(){
        portScanner = new HightPortScanner();
    }

    @Test
    public void testPreconditions(){
       assertNotNull(portScanner);
    }

    @Test
    public void testPortScannerScanPerfectly(){
        portScanner.scan();
    }

}
