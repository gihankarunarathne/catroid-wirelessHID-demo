package com.example.catroid_bt_app.hid;

import java.util.ArrayList;
import java.util.Collection;

import android.util.Log;

public class Communicator {
    // Debugging
    private static boolean D = true;
    private static String TAG = "Catrid-BT";

    private WirelessManager hid;

    public Communicator(WirelessManager hid) {
	this.hid = hid;
    }

    public void setMACAddress(String macaddress) {
	hid.setMACAddress(macaddress);
    }

    public void start() {
	hid.startCommunicator();
    }

    public void stop() {
	hid.stopCommunicator();
    }

    // public void send(String msg) {
    // try {
    // byte[] byte_msg = msg.getBytes("US-ASCII");
    // byte[] val = { 0, 0, 0, 1, 0, 0, 0, 0 };
    // hid.sendMessage(val);
    // byte[] key = { 0, 1, 0, 0, 0, 0, 0, 1 };
    // hid.sendMessage(key);
    // Log.i(TAG, "Communicator:Send msg :" + byte_msg);
    // } catch (UnsupportedEncodingException e) {
    // if (D)
    // Log.e(TAG, "Communicator:Error send msg ", e);
    // }
    // }

    public int getModifierCode(int keyValue) {

	int modifier;

	switch (keyValue) {
	case 224: // emulate STRG
	    modifier = 0x01;
	    break;
	case 225: // emulate SHIFT_LEFT
	    modifier = 0x02;
	    break;
	case 229: // emulate SHIFT_RIGHT
	    modifier = 0x20;
	    break;
	case 226: // emulate ALT_LEFT
	    modifier = 0x04;
	    break;
	case 230: // emulate ALT_RIGHT
	    modifier = 0x40;
	    break;
	default:
	    modifier = 0x00;
	    break;
	}

	return modifier;
    }

    public int getMouseActiion(int eventType, int value) {
	int combine = 0;

	switch (eventType) {
	case 2: // set Mouse click
	    combine = value;
	    break;
	case 3: // set mouse wheel +
	    combine = value;
	    break;
	case 4: // set mouse wheel -
	    combine = value;
	    combine |= 0x128;
	    break;
	case 5: // set mouse x+, move y+
	    combine |= value;
	    break;
	case 6: // set mouse x+, move y-
	    combine |= value;
	    combine |= 0x8;
	    break;
	case 7: // set mouse y+, move x+
	    combine |= value << 4;
	    break;
	case 8: // set mouse y+, move x-
	    combine |= value << 4;
	    combine |= 0x128;
	    break;
	}

	return combine;
    }

    public byte[] generateHidCode(Collection<KeyCode> keys) {
	if (D)
	    Log.d("catBT", "genHIDcode: num# " + keys.size());
	for (KeyCode key : keys) {
	    Log.d("catBT", "genHIDcode: mod " + key.isModifier() + " val "
		    + key.getKeyCode());
	}
	int[] hidCode = new int[] { 161, 1, 0, 0, 0, 0, 0, 0, 0, 0 };

	int i = 4;
	for (KeyCode key : keys) {
	    if (key.getEventType() > 1) {
		hidCode[1] = key.getEventType();
		hidCode[3] = getMouseActiion(key.getEventType(),
			key.getKeyCode());
	    } else if (key.isModifier()) {
		hidCode[2] |= getModifierCode(key.getKeyCode());
	    } else {
		if (i < 10) {
		    hidCode[i] = key.getKeyCode();
		    i++;
		}
	    }
	}

	byte[] conv = new byte[10];

	for (int j = 0; j < 10; j++) {
	    conv[j] = (byte) (hidCode[j]);
	}
	return conv;
    }

    public void send(KeyCode key) {

	// byte[] data = String.valueOf(key.getKeyCode()).getBytes();

	if (hid == null) {
	    Log.e(TAG, "Communicator no available!!");
	    return;
	}
	Collection<KeyCode> c = new ArrayList<KeyCode>();
	c.add(key);
	hid.sendMessage(generateHidCode(c));
    }

    public void send(Collection<KeyCode> keys) {
	if (hid == null) {
	    Log.e(TAG, "Communicator no available!!");
	    return;
	}
	hid.sendMessage(generateHidCode(keys));
    }

}
