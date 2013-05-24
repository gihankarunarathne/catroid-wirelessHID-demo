package com.example.catroid_bt_app.ui.test;

import android.bluetooth.BluetoothAdapter;
import android.test.ActivityInstrumentationTestCase2;

import com.example.catroid_bt_app.ui.MouseActivity;
import com.jayway.android.robotium.solo.Solo;

public class MouseActivityTest extends
        ActivityInstrumentationTestCase2<MouseActivity> {
    private Solo solo;
    private String SERVER_MAC_ADDRESS = "70:F3:95:A4:7F:67";
    MouseActivity mouse;

    public MouseActivityTest() {
        super(MouseActivity.class);
    }

    public void setUp() throws Exception {
        try {
            solo = new Solo(getInstrumentation(), getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testOnClick() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        assertTrue("Bluetooth not supported on device",
                bluetoothAdapter != null);
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
            solo.sleep(2000);
        }

        solo.sleep(500);
        solo.clickOnButton("Connect");
        solo.sleep(500);
        solo.clickOnButton("Search Bluetooth devices");
        solo.sleep(1000);
        solo.clickOnMenuItem("ubuntu-gc");
        solo.sleep(2000);
        solo.drag(300, 600, 200, 500, 10);
        solo.sleep(500);
        solo.clickOnButton("Left");
        solo.sleep(500);
        solo.clickOnButton("Right");
        solo.sleep(500);
        solo.clickOnButton("M");
        solo.sleep(500);
        
        
        solo.clickOnButton("DisConnect");
        solo.sleep(2000);
    }

}
