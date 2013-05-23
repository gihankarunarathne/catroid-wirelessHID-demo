package com.example.catroid_bt_app.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.InputEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.catroid_bt_app.R;
import com.example.catroid_bt_app.bluetooth.BluetoothManager;
import com.example.catroid_bt_app.bluetooth.DeviceListActivity;
import com.example.catroid_bt_app.hid.Communicator;
import com.example.catroid_bt_app.hid.KeyCode;

@SuppressLint("NewApi")
public class MouseActivity extends Activity implements OnClickListener {
    // Debugging
    private static final String TAG = "Catroid-BT";
    private static final boolean D = true;

    private Communicator com = null;
    // Intent request codes
    private static final int REQUEST_ENABLE_BT = 1;
    // MacAddress
    private String macaddress;

    LinearLayout layout, container, buttons;
    TextView text;
    Button b_connect, b_left, b_right, b_middle;
    MultiTouchPad touchPad;

    public static final int BUTTON1_DOWN = 100;
    public static final int BUTTON2_DOWN = 101;
    public static final int BUTTON3_DOWN = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.com = new Communicator(BluetoothManager.getBluetoothManager(this));

        layout = new LinearLayout(this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        container = new LinearLayout(this);
        container.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        container.setOrientation(LinearLayout.HORIZONTAL);
        buttons = new LinearLayout(this);
        buttons.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        buttons.setOrientation(LinearLayout.HORIZONTAL);

        touchPad = new MultiTouchPad(this, this.com);
        touchPad.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        touchPad.setBackgroundColor(Color.GRAY);
        text = new TextView(this);
        text.setText("Press Start to Connect ...");
        text.setLayoutParams(new TableLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
        b_connect = new Button(this);
        b_connect.setText("Connect");
        b_connect.setOnClickListener(this);
        b_connect.setId(101);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        b_left = new Button(this);
        b_left.setText("Left");
        b_left.setOnClickListener(this);
        b_left.setWidth(Math.round(width) * 2 / 5);
        b_left.setId(102);
        b_middle = new Button(this);
        b_middle.setText("");
        b_middle.setOnClickListener(this);
        b_middle.setWidth(Math.round(width) / 5);
        b_middle.setId(103);
        b_right = new Button(this);
        b_right.setText("Right");
        b_right.setOnClickListener(this);
        b_right.setWidth(Math.round(width) * 2 / 5);
        b_right.setId(104);

        container.addView(text);
        container.addView(b_connect);
        buttons.addView(b_left);
        buttons.addView(b_middle);
        buttons.addView(b_right);
        layout.addView(container);
        layout.addView(buttons);
        layout.addView(touchPad);
        setContentView(layout);
    }

    public void onClick(View v) {
        Log.i(TAG, String.valueOf(v.getId()));
        switch (v.getId()) {
        case 101:
            // Launch the DeviceListActivity to see devices and do scan
            Intent serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_ENABLE_BT);
            text.setText("Request Connecting..");
            break;

        case 102:
            com.send(new KeyCode(2, BUTTON1_DOWN));
            text.setText("click left");
            break;
        case 103:
            com.send(new KeyCode(2, BUTTON2_DOWN));
            text.setText("click middle");
            break;
        case 104:
            com.send(new KeyCode(2, BUTTON3_DOWN));
            text.setText("click right");
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (D)
            Log.d(TAG, "Key:onActivityResult " + resultCode);

        if (requestCode == REQUEST_ENABLE_BT) {
            // Get the device MAC address
            this.macaddress = data.getExtras().getString(
                    DeviceListActivity.EXTRA_DEVICE_ADDRESS);
            text.setText("Select " + macaddress);
            com.setMACAddress(macaddress);
            com.start();
            Log.i(TAG, "Key:Started Communicator");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mouse, menu);
        return true;
    }

}
