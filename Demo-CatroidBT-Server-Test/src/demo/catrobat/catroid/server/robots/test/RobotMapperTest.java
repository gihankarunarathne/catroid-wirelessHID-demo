package demo.catrobat.catroid.server.robots.test;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import junit.framework.TestCase;

import demo.catrobat.catroid.server.robots.RobotMapper;

public class RobotMapperTest extends TestCase {

    public void testLookup() {
        try {
            RobotMapper rm = new RobotMapper();

            byte b = 4;
            Integer a = rm.lookup(b);
            if ((a == null) || (a != KeyEvent.VK_A))
                assertTrue(false);

            b = 29;
            Integer z = rm.lookup(b);
            if ((z == null) || (z != KeyEvent.VK_Z))
                assertTrue(false);

            b = 39;
            Integer zero = rm.lookup(b);
            if ((zero == null) || (zero != KeyEvent.VK_0))
                assertTrue(false);

            b = 100;
            Integer hundred = rm.lookup(b);
            if ((hundred == null) || (hundred != InputEvent.BUTTON1_MASK))
                assertTrue(false);

            b = 101;
            Integer hundredOne = rm.lookup(b);
            if ((hundredOne == null) || (hundredOne != InputEvent.BUTTON2_MASK))
                assertTrue(false);

            b = 102;
            Integer hundredTwo = rm.lookup(b);
            if ((hundredTwo == null) || (hundredTwo != InputEvent.BUTTON3_MASK))
                assertTrue(false);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    public void testGetKeyList() {
        byte[] values = new byte[10];

        values[0] = 0;
        values[1] = 0;
        values[2] = 0x67;
        values[3] = 0;
        values[4] = 29;
        values[5] = 29;
        values[6] = 29;
        values[7] = 29;
        values[8] = 29;
        values[9] = 29;

        try {
            RobotMapper rm = new RobotMapper();

            ArrayList<Integer> list = rm.getKeyList(values);

            assertNotNull(list);
            assertTrue(list.size() > 0);
            assertTrue(list.get(0) == KeyEvent.VK_CONTROL);
            assertTrue(list.get(1) == KeyEvent.VK_SHIFT);
            assertTrue(list.get(2) == KeyEvent.VK_SHIFT);
            assertTrue(list.get(3) == KeyEvent.VK_ALT);
            assertTrue(list.get(4) == KeyEvent.VK_ALT_GRAPH);
            assertTrue(list.size() == 11);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    public void testExtractMouseValue() {
        int eventType = 2;
        byte value = 100;

        try {
            RobotMapper rm = new RobotMapper();
            assertEquals("Button 1 Down ", 16, rm.extractMouseValue(eventType, value));
            value = 101;
            assertEquals("Button 2 Down ", 8, rm.extractMouseValue(eventType, value));
            value = 102;
            assertEquals("Button 3 Down ", 4, rm.extractMouseValue(eventType, value));
            
            eventType = 3; value = 127;
            assertEquals("Wheel Scroll + ", 127, rm.extractMouseValue(eventType, value));
            eventType = 4; value = -127; value |= 0x80;
            assertEquals("Wheel Scroll - ", value, rm.extractMouseValue(eventType, value));
            
            eventType = 5; value = 127;
            assertEquals("Mouse Move Y + ", 127, rm.extractMouseValue(eventType, value));
            eventType = 6; value = -127; value |= 0x80;
            assertEquals("Mouse Move Y - ", value, rm.extractMouseValue(eventType, value));
            
            eventType = 7; value = 127;
            assertEquals("Mouse Move X + ", 127, rm.extractMouseValue(eventType, value));
            eventType = 8; value = -127; value |= 0x80;
            assertEquals("Mouse Move X - ", value, rm.extractMouseValue(eventType, value));
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    public void testIllegalLookup() {
        try {
            RobotMapper rm = new RobotMapper();
            byte b = -1;
            Integer i = rm.lookup(b);
            if (i != null)
                assertTrue(false);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}