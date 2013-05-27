package com.example.catroid_bt_app.ui.test;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.graphics.Point;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.Display;

import com.example.catroid_bt_app.ui.MouseActivity;
import com.jayway.android.robotium.solo.Solo;

@SuppressLint("NewApi")
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

        solo.sleep(200);
        solo.clickOnButton("Disconnect");
        solo.sleep(200);
        solo.clickOnButton("Search Bluetooth devices");
        solo.sleep(500);
        solo.clickInList(0, 0);
        solo.sleep(3000);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float width = size.x;
        float height = size.y;
        Log.i("Catroid-BT", "width:" + width + " height:" + height);

        for (int i = 0; i < 50; i++) {
            solo.drag(width / 2, width / 2 + 11, height / 2, height / 2, 1);
            solo.sleep(10);
        }
        solo.sleep(50);
        for (int i = 0; i < 50; i++) {
            solo.drag(width / 2, width / 2, height / 2 + 11, height / 2, 1);
            solo.sleep(10);
        }
        solo.sleep(50);
        for (int i = 0; i < 50; i++) {
            solo.drag(width / 2, width / 2 - 11, height / 2, height / 2, 1);
            solo.sleep(10);
        }
        solo.sleep(50);
        for (int i = 0; i < 50; i++) {
            solo.drag(width / 2, width / 2, height / 2 - 11, height / 2, 1);
            solo.sleep(10);
        }
        solo.sleep(50);

        // -------
        for (int i = 0; i < 50; i++) {
            solo.drag(width / 2, width / 2 + 11, height / 2, height / 2, 1);
            solo.drag(width / 2, width / 2, height / 2 + 11, height / 2, 1);
            solo.sleep(20);
        }
        solo.sleep(50);
        for (int i = 0; i < 50; i++) {
            solo.drag(width / 2, width / 2 - 11, height / 2, height / 2, 1);
            solo.drag(width / 2, width / 2, height / 2 - 11, height / 2, 1);
            solo.sleep(20);
        }
        solo.sleep(50);
        for (int i = 0; i < 50; i++) {
            solo.drag(width / 2, width / 2 + 11, height / 2, height / 2, 1);
            solo.drag(width / 2, width / 2, height / 2 - 11, height / 2, 1);
            solo.sleep(20);
        }
        solo.sleep(50);
        for (int i = 0; i < 50; i++) {
            solo.drag(width / 2, width / 2 - 11, height / 2, height / 2, 1);
            solo.drag(width / 2, width / 2, height / 2 + 11, height / 2, 1);
            solo.sleep(20);
        }
        solo.sleep(50);

        solo.clickOnButton("Left");
        solo.sleep(500);
        solo.clickOnButton("Right");
        solo.sleep(500);
        solo.clickOnButton("M");
        solo.sleep(500);

        solo.clickOnButton("Connect");
        solo.sleep(1000);
    }

}
