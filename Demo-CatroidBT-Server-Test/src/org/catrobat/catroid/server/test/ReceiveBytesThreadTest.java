package org.catrobat.catroid.server.test;

//import java.io.IOException;
//
//import javax.bluetooth.UUID;
//import javax.microedition.io.Connector;
//import javax.microedition.io.StreamConnectionNotifier;
//
//import junit.framework.TestCase;
//
//import org.junit.Test;
//
//public class ReceiveBytesThreadTest extends TestCase{
//    
//    @Test
//    public void testLookupCMD(){
//        UUID uuid = new UUID("04c6093b00001000800000805f9b34fb", false);
//        String SERVER_NAME = "WirelesHumanInterfaceServer";
//        String url = "btspp://localhost:" + uuid.toString() + ";name=" + SERVER_NAME;
//        StreamConnectionNotifier notifier = null;
//        try {
//            notifier = (StreamConnectionNotifier) Connector.open(url);
//        } catch (IOException e) {
//            assertTrue(false);
//        }
//        //ReceiveBytesThread receiver = new ReceiveBytesThread(notifier.acceptAndOpen(), false);
//        
//        byte[] input = new byte[10];
//        
//        byte[] exitCode = {-1};
//        //assertEquals("Exit Code ", -1, ); 
//    }
//}