package com.example.catroid_bt_app.hid.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import android.test.AndroidTestCase;

import com.example.catroid_bt_app.hid.Communicator;
import com.example.catroid_bt_app.hid.KeyCode;
import com.example.catroid_bt_app.ui.MouseActivity;

public class CommunicatorTest extends AndroidTestCase {

    private Communicator com;

    @Override
    protected void setUp() throws Exception {
        // com = new Communicator(BluetoothManager.getBluetoothManager(new MouseActivity()));
        com = new Communicator(null);
    }

    public void testGenerateHidCode() {

        KeyCode valueKey = new KeyCode(false, 4);
        KeyCode modifierKey = new KeyCode(true, 230);

        Collection<KeyCode> keyList = new ArrayList<KeyCode>();
        keyList.add(valueKey);
        keyList.add(modifierKey);
        byte[] returnArr = com.generateHidCode(keyList);

        for (byte blub : returnArr) {
            System.out.println(blub);
        }

        assertEquals("Test index 0 value", -95, returnArr[0]);
        assertEquals("Test modifier value", 64, returnArr[2]);
        assertEquals("Test key value", 4, returnArr[4]);

        KeyCode valueKey1 = new KeyCode(false, 8);
        KeyCode modifierKey1 = new KeyCode(true, 230);
        KeyCode modifierKey2 = new KeyCode(true, 224);

        keyList = new ArrayList<KeyCode>();
        keyList.add(valueKey1);
        keyList.add(modifierKey1);
        keyList.add(modifierKey2);

        returnArr = com.generateHidCode(keyList);
        assertEquals("Test index 0 value", -95, returnArr[0]);
        assertEquals("Test modifier value", 0x40 | 0x01, returnArr[2]);
        assertEquals("Test key value", 8, returnArr[4]);

        keyList = new ArrayList<KeyCode>();
        keyList.add(new KeyCode(false, 5));
        keyList.add(new KeyCode(false, 8));
        keyList.add(new KeyCode(false, 16));
        keyList.add(new KeyCode(false, 27));
        keyList.add(new KeyCode(false, 30));
        keyList.add(new KeyCode(false, 39));
        keyList.add(new KeyCode(false, 6));
        keyList.add(new KeyCode(false, 9));
        keyList.add(new KeyCode(false, 9));

        returnArr = com.generateHidCode(keyList);
        assertEquals("Test index 0 value", -95, returnArr[0]);
        assertEquals("Test index 1 value", 1, returnArr[1]);
        assertEquals("Test index 4 value", 5, returnArr[4]);
        assertEquals("Test index 5 value", 8, returnArr[5]);
        assertEquals("Test index 6 value", 16, returnArr[6]);
        assertEquals("Test index 7 value", 27, returnArr[7]);
        assertEquals("Test index 8 value", 30, returnArr[8]);
        assertEquals("Test index 9 value", 39, returnArr[9]);

        keyList = new ArrayList<KeyCode>();
        keyList.add(new KeyCode(true, 224));
        keyList.add(new KeyCode(true, 226));
        keyList.add(new KeyCode(false, 76));

        returnArr = com.generateHidCode(keyList);
        assertEquals("Test index 0 value", -95, returnArr[0]);
        assertEquals("Test index 1 value", 1, returnArr[1]);
        assertEquals("Test modifier value", 0x04 | 0x01, returnArr[2]);
        assertEquals("Test index 4 value", 76, returnArr[4]);
        assertEquals("Test index 5 value", 0, returnArr[5]);
        assertEquals("Test index 6 value", 0, returnArr[6]);
        assertEquals("Test index 7 value", 0, returnArr[7]);
        assertEquals("Test index 8 value", 0, returnArr[8]);
        assertEquals("Test index 9 value", 0, returnArr[9]);

        keyList = new ArrayList<KeyCode>();
        keyList.add(new KeyCode(false, 69));

        returnArr = com.generateHidCode(keyList);
        assertEquals("Test index 0 value", -95, returnArr[0]);
        assertEquals("Test index 1 value", 1, returnArr[1]);
        assertEquals("Test modifier value", 0, returnArr[2]);
        assertEquals("Test index 4 value", 69, returnArr[4]);

        keyList = new ArrayList<KeyCode>();
        keyList.add(new KeyCode(2, MouseActivity.BUTTON1_DOWN));
        returnArr = com.generateHidCode(keyList);
        assertEquals("Test index 0 value", -95, returnArr[0]);
        assertEquals("Test index 1 value", 2, returnArr[1]);
        assertEquals("Test index 2 value", 0, returnArr[2]);
        assertEquals("Test index 3 value", 100, returnArr[3]);
        assertEquals("Test index 4 value", 0, returnArr[4]);
        assertEquals("Test index 5 value", 0, returnArr[5]);
        assertEquals("Test index 6 value", 0, returnArr[6]);
        assertEquals("Test index 7 value", 0, returnArr[7]);
        assertEquals("Test index 8 value", 0, returnArr[8]);
        assertEquals("Test index 9 value", 0, returnArr[9]);

        keyList = new ArrayList<KeyCode>();
        keyList.add(new KeyCode(2, MouseActivity.BUTTON2_DOWN));
        returnArr = com.generateHidCode(keyList);
        assertEquals("Test index 0 value", -95, returnArr[0]);
        assertEquals("Test index 1 value", 2, returnArr[1]);
        assertEquals("Test index 2 value", 0, returnArr[2]);
        assertEquals("Test index 3 value", 101, returnArr[3]);

        keyList = new ArrayList<KeyCode>();
        keyList.add(new KeyCode(2, MouseActivity.BUTTON3_DOWN));
        returnArr = com.generateHidCode(keyList);
        assertEquals("Test index 0 value", -95, returnArr[0]);
        assertEquals("Test index 1 value", 2, returnArr[1]);
        assertEquals("Test index 2 value", 0, returnArr[2]);
        assertEquals("Test index 3 value", 102, returnArr[3]);

        keyList = new ArrayList<KeyCode>();
        int scroll = new Random().nextInt(127);
        keyList.add(new KeyCode(3, scroll));
        returnArr = com.generateHidCode(keyList);
        assertEquals("Test index 0 value", -95, returnArr[0]);
        assertEquals("Test index 1 value", 3, returnArr[1]);
        assertEquals("Test index 2 value", 0, returnArr[2]);
        assertEquals("Test index 3 value", scroll, returnArr[3]);

        keyList = new ArrayList<KeyCode>();
        scroll = new Random().nextInt(127) * -1;
        keyList.add(new KeyCode(4, scroll));
        returnArr = com.generateHidCode(keyList);
        assertEquals("Test index 0 value", -95, returnArr[0]);
        assertEquals("Test index 1 value", 4, returnArr[1]);
        assertEquals("Test index 2 value", 0, returnArr[2]);
        assertEquals("Test index 3 value", scroll | 0x80, returnArr[3]);

        keyList = new ArrayList<KeyCode>();
        int dx = new Random().nextInt(127);
        keyList.add(new KeyCode(5, dx));
        returnArr = com.generateHidCode(keyList);
        assertEquals("Test index 0 value", -95, returnArr[0]);
        assertEquals("Test index 1 value", 5, returnArr[1]);
        assertEquals("Test index 2 value", 0, returnArr[2]);
        assertEquals("Test index 3 value", dx, returnArr[3]);

        keyList = new ArrayList<KeyCode>();
        dx = new Random().nextInt(127) * -1;
        keyList.add(new KeyCode(6, dx));
        returnArr = com.generateHidCode(keyList);
        assertEquals("Test index 0 value", -95, returnArr[0]);
        assertEquals("Test index 1 value", 6, returnArr[1]);
        assertEquals("Test index 2 value", 0, returnArr[2]);
        assertEquals("Test index 3 value", dx | 0x80, returnArr[3]);

        keyList = new ArrayList<KeyCode>();
        int dy = new Random().nextInt(127);
        keyList.add(new KeyCode(7, dy));
        returnArr = com.generateHidCode(keyList);
        assertEquals("Test index 0 value", -95, returnArr[0]);
        assertEquals("Test index 1 value", 7, returnArr[1]);
        assertEquals("Test index 2 value", 0, returnArr[2]);
        assertEquals("Test index 3 value", dy, returnArr[3]);

        keyList = new ArrayList<KeyCode>();
        dy = new Random().nextInt(127) * -1;
        keyList.add(new KeyCode(8, dy));
        returnArr = com.generateHidCode(keyList);
        assertEquals("Test index 0 value", -95, returnArr[0]);
        assertEquals("Test index 1 value", 8, returnArr[1]);
        assertEquals("Test index 2 value", 0, returnArr[2]);
        assertEquals("Test index 3 value", dy | 0x80, returnArr[3]);
    }

    public void testGenerateMouseAction() {
        int combine = 0;

        combine = com.getMouseActiion(2, MouseActivity.BUTTON1_DOWN);
        assertEquals("Test event type 2", 100, combine);

        combine = com.getMouseActiion(2, MouseActivity.BUTTON2_DOWN);
        assertEquals("Test event type 2", 101, combine);

        combine = com.getMouseActiion(2, MouseActivity.BUTTON3_DOWN);
        assertEquals("Test event type 2", 102, combine);

        int scroll = new Random().nextInt(127);
        combine = com.getMouseActiion(3, scroll);
        assertEquals("Test event type 3", scroll, combine);

        scroll = new Random().nextInt(127) * -1;
        combine = com.getMouseActiion(4, scroll);
        assertEquals("Test event type 4", scroll | 0x80, combine);

        int dx = new Random().nextInt(127);
        combine = com.getMouseActiion(5, dx);
        assertEquals("Test event type 5", dx, combine);

        dx = new Random().nextInt(127) * -1;
        combine = com.getMouseActiion(6, dx);
        assertEquals("Test event type 6", dx | 0x80, combine);

        int dy = new Random().nextInt(127);
        combine = com.getMouseActiion(7, dy);
        assertEquals("Test event type 7", dy, combine);

        dy = new Random().nextInt(127) * -1;
        combine = com.getMouseActiion(8, dy);
        assertEquals("Test event type 8", dy | 0x80, combine);
    }
}
