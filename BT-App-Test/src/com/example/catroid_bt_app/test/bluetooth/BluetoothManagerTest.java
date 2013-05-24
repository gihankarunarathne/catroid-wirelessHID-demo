package com.example.catroid_bt_app.test.bluetooth;

import org.junit.Before;
import org.junit.Test;

import android.test.AndroidTestCase;

import com.example.catroid_bt_app.bluetooth.BluetoothManager;
import com.example.catroid_bt_app.ui.KeyBoardActivity;

public class BluetoothManagerTest extends AndroidTestCase {
    private BluetoothManager bluetooth = null;
    
    @Before
    public void setUpBluetoothManagerTest() {
        this.bluetooth = BluetoothManager.getBluetoothManager( new KeyBoardActivity() );
    }   
    
    @Test
    public void steps(){
        String MACAddress = ""; // tmp MACAddress
        bluetooth.setMACAddress(MACAddress);
        bluetooth.startCommunicator();
        
        byte[] msg = new byte[10];
        msg[1] = 2;
        msg[3] = 100;
        bluetooth.sendMessage(msg);
        bluetooth.stopCommunicator();
        
        bluetooth.startCommunicator();
        
        msg = new byte[10];
        msg[1] = 3;
        msg[3] = 100;
        bluetooth.sendMessage(msg);
    }
}
