package com.example.catroid_bt_app.hid;

import java.util.ArrayList;
import java.util.Collection;

import android.util.Log;

public class Communicator {
    // Debugging
    private static boolean D = false;
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
        if (value > 127) // most significant bit use for sign convention
            value = 127;

        switch (eventType) {
        case 2: // set Mouse click
            combine = value;
            break;
        case 3: // set mouse wheel +
            combine = value;
            break;
        case 4: // set mouse wheel -
            combine = value | 0x80;
            break;
        case 5: // set mouse static x and move y+
            combine = value;
            break;
        case 6: // set mouse static x and move y-
            combine = value | 0x80;
            break;
        case 7: // set mouse static y move x+
            combine = value;
            break;
        case 8: // set mouse static y move x-
            combine = value | 0x80;
            break;
        }

        return combine;
    }

    public byte[] generateHidCode(Collection<KeyCode> keys) {
        if (D) {
            Log.d(TAG, "num# of keys " + keys.size());
            for (KeyCode key : keys) {
                Log.d(TAG, "key is modify: " + key.isModifier() + " keyValue "
                        + key.getKeyCode());
            }
        }

        int[] hidCode = new int[] { 161, 1, 0, 0, 0, 0, 0, 0, 0, 0 };

        int i = 4;
        for (KeyCode key : keys) {
            if (D)
                Log.i(TAG, "Event Type:" + key.getEventType());

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
            if (D && j == 3)
                Log.d(TAG, "eventtype:" + conv[1] + " msg index 3:"
                        + hidCode[3] + " to " + conv[j]);
        }
        return conv;
    }

    public void send(KeyCode key) {
        if (hid == null) {
            Log.e(TAG, "Communicator not available!!");
            return;
        }
        Collection<KeyCode> c = new ArrayList<KeyCode>();
        c.add(key);
        hid.sendMessage(generateHidCode(c));
    }

    public void send(Collection<KeyCode> keys) {
        if (hid == null) {
            Log.e(TAG, "Communicator not available!!");
            return;
        }
        hid.sendMessage(generateHidCode(keys));
    }
}