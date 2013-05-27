package com.example.catroid_bt_app.ui.test;

import java.util.ArrayList;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.catroid_bt_app.R;
import com.example.catroid_bt_app.bluetooth.BluetoothManager;
import com.example.catroid_bt_app.ui.KeyBoardActivity;
import com.jayway.android.robotium.solo.Solo;

public class KeyBoardActivityTest extends
	ActivityInstrumentationTestCase2<KeyBoardActivity> {
    private Solo solo;
    private BluetoothManager bM = null;

    public KeyBoardActivityTest() {
	
	super(KeyBoardActivity.class);
	
    }

    public void setUp() throws Exception {
	try {
	    solo = new Solo(getInstrumentation(), getActivity());
	    bM = BluetoothManager.getBluetoothManager(getActivity());
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

	solo.clickOnView(solo.getView(R.id.button_a));
	solo.sleep(500);
	solo.clickOnToggleButton("Disconnect");
	solo.sleep(500);

	solo.clickInList(0, 0);
	solo.sleep(1000);
	boolean isConnect = bM.isConnected();
	isConnect = true; // temp decision, devices are paired already
	if (isConnect) {
	    solo.sleep(3000);
	} else {
	    solo.sleep(100);
	    bluetoothAdapter.startDiscovery();
	    solo.sleep(500);
	    solo.clickOnButton("Search Bluetooth devices");
	    solo.sleep(4000);
	    solo.clickInList(0, 1);
	    bluetoothAdapter.cancelDiscovery();
	}

	solo.clickOnView(solo.getView(R.id.button_q));
	solo.sleep(500);
	solo.clickLongOnView(solo.getView(R.id.button_l_shift), 500);
	solo.sleep(10);
	solo.clickOnView(solo.getView(R.id.button_q));
	solo.sleep(500);
	solo.clickOnView(solo.getView(R.id.button_space));
	solo.sleep(500);

	solo.clickLongOnView(solo.getView(R.id.button_l_shift), 500);
	solo.clickLongOnView(solo.getView(R.id.button_l_ctrl), 500);
	solo.clickOnView(solo.getView(R.id.button_a));
	solo.sleep(500);

	solo.clickOnToggleButton("Connect");
	solo.sleep(1000);
    }

}