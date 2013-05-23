package org.catrobat.catroid.test.server;

import junit.framework.TestCase;

public class ReceiveBytesThreadTest extends TestCase{
    public void testLookupCMD(){
        byte[] input = new byte[10];
        
        byte[] exitCode = {-1};
        assertEquals("Exit Code ", -1, exitCode);
        
    }
}
