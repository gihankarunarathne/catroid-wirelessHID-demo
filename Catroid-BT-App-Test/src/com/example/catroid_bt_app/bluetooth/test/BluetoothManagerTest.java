package com.example.catroid_bt_app.bluetooth.test;

import android.bluetooth.BluetoothAdapter;
import android.test.ActivityInstrumentationTestCase2;

import com.example.catroid_bt_app.bluetooth.BluetoothManager;
import com.example.catroid_bt_app.ui.KeyBoardActivity;
import com.jayway.android.robotium.solo.Solo;

public class BluetoothManagerTest extends ActivityInstrumentationTestCase2<KeyBoardActivity> {
   
    private BluetoothManager bluetooth = null;
    String eq = "70:F3:95:A4:7F:67";   //tmp MACAddress
    private Solo solo;
    
    public BluetoothManagerTest() {
        super(KeyBoardActivity.class);
    }
    
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
          this.bluetooth = BluetoothManager.getBluetoothManager(this.getActivity());
        super.setUp();
    }
    
    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        getActivity().finish();
        super.tearDown();
    }

    public void testBluetoothManager() {
         BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                 .getDefaultAdapter();
         assertTrue("Bluetooth not supported on device",
                 bluetoothAdapter != null);
         if (!bluetoothAdapter.isEnabled()) {
             bluetoothAdapter.enable();
             solo.sleep(5000);
         }

         bluetooth.setMACAddress(eq);
         bluetooth.startCommunicator();
         solo.sleep(4000);
 
         byte[] msg = new byte[10];
         msg[1] = 2;
         msg[3] = 100;
         bluetooth.sendMessage(msg);
         solo.sleep(1000);
         bluetooth.stopCommunicator();
         solo.sleep(1000);
 
         bluetooth.startCommunicator();
         msg = new byte[10];
         msg[4] = 4;
         solo.sleep(3000);
         bluetooth.sendMessage(msg);
 
         msg = new byte[10];
         msg[3] = 0x02;
         msg[4] = 4;
         bluetooth.sendMessage(msg);
         bluetooth.stopCommunicator();
         solo.sleep(1000);
    }
}
