package com.example.catroid_bt_app.test.hid;

import java.util.ArrayList;
import java.util.Collection;

import android.test.AndroidTestCase;

import com.example.catroid_bt_app.bluetooth.BluetoothManager;
import com.example.catroid_bt_app.hid.Communicator;
import com.example.catroid_bt_app.hid.KeyCode;
import com.example.catroid_bt_app.ui.MouseActivity;

public class CommunicatorTest extends AndroidTestCase {

    private Communicator com;

    @Override
	protected void setUp() throws Exception {
		com = new Communicator(BluetoothManager.getBluetoothManager(new MouseActivity()));
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
	assertEquals("Test index 2 value", 250, returnArr[2]);
	assertEquals("Test index 4 value", 0, returnArr[4]);
	assertEquals("Test index 5 value", 0, returnArr[5]);
	assertEquals("Test index 6 value", 0, returnArr[6]);
	assertEquals("Test index 7 value", 0, returnArr[7]);
	assertEquals("Test index 8 value", 0, returnArr[8]);
	assertEquals("Test index 9 value", 0, returnArr[9]);
    }
    
    public void testGenerateMouseAction() {
	int combine = 0;
	
	combine = com.getMouseActiion(2, MouseActivity.BUTTON1_DOWN);
	assertEquals("Test event type 2",250, combine);
    }
}
