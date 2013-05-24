package com.example.catroid_bt_app.bluetooth.test;

import org.junit.Before;
import org.junit.Test;

import android.test.AndroidTestCase;

import com.example.catroid_bt_app.bluetooth.BluetoothManager;
import com.example.catroid_bt_app.ui.KeyBoardActivity;

public class BluetoothManagerTest extends AndroidTestCase {
    private BluetoothManager bluetooth = null;
    String MACAddress = "70:F3:95:A4:7F:67"; // tmp MACAddress
    
    @Before
    public void setUpBluetoothManagerTest() {
        this.bluetooth = BluetoothManager.getBluetoothManager( new KeyBoardActivity() );
    }   
    
    @Test
    public void steps(){
        bluetooth.setMACAddress(MACAddress);
        bluetooth.startCommunicator();
        
        byte[] msg = new byte[10];
        msg[1] = 2;
        msg[3] = 100;
        bluetooth.sendMessage(msg);
        bluetooth.stopCommunicator();
        
        bluetooth.startCommunicator();
        msg = new byte[10];
        msg[4] = 4;
        bluetooth.sendMessage(msg);
        
        msg = new byte[10];
        msg[3] = 0x02;
        msg[4] = 4;
        bluetooth.sendMessage(msg);
        bluetooth.stopCommunicator();
    }
}
