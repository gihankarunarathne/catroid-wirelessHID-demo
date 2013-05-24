package com.example.catroid_bt_app.ui.test;

import android.bluetooth.BluetoothAdapter;
import android.test.ActivityInstrumentationTestCase2;

import com.example.catroid_bt_app.R;
import com.example.catroid_bt_app.ui.KeyBoardActivity;
import com.jayway.android.robotium.solo.Solo;

public class KeyBoardActivityTest extends ActivityInstrumentationTestCase2<KeyBoardActivity> {
    private Solo solo;
    private String SERVER_MAC_ADDRESS = "70:F3:95:A4:7F:67";

    public KeyBoardActivityTest() {
        super(KeyBoardActivity.class);
    }

    public void setUp() throws Exception {
        try{
        solo = new Solo(getInstrumentation(), getActivity());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }
    
    public void testOnClick(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        assertTrue("Bluetooth not supported on device", bluetoothAdapter != null);
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
            solo.sleep(2000);
        }
        
        solo.clickOnView(solo.getView(R.id.button_a));
        solo.sleep(500);
        solo.clickOnToggleButton("Disconnect");
        solo.sleep(500);
        solo.clickOnButton("Search Bluetooth devices");
        solo.sleep(1000);
        solo.clickOnMenuItem("ubuntu-gc"); // configure as necessary
        solo.sleep(3000);
        
        solo.clickOnView(solo.getView(R.id.button_q));
        solo.sleep(500);
        solo.clickLongOnView(solo.getView(R.id.button_l_shift),500);
        solo.sleep(10);
        solo.clickOnView(solo.getView(R.id.button_q));
        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.button_space));
        solo.sleep(500);
        
        solo.clickLongOnView(solo.getView(R.id.button_l_shift),500);
        solo.clickLongOnView(solo.getView(R.id.button_l_ctrl),500);
        solo.clickOnView(solo.getView(R.id.button_a));
        solo.sleep(500);
        
        solo.clickOnToggleButton("Connect");
        solo.sleep(1000);
    }

}