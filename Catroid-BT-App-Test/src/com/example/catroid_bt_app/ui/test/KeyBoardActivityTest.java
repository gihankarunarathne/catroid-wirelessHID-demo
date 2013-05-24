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
        solo.clickOnButton("Connect");
        solo.sleep(500);
        solo.clickOnButton("Search Bluetooth devices");
        solo.sleep(2000);
        solo.clickOnMenuItem("ubuntu-gc");
        solo.sleep(5000);
    }

}