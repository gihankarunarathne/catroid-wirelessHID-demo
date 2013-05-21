package com.example.catroid_bt_app.ui;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.catroid_bt_app.R;
import com.example.catroid_bt_app.bluetooth.BluetoothManager;
import com.example.catroid_bt_app.bluetooth.DeviceListActivity;
import com.example.catroid_bt_app.hid.Communicator;
import com.example.catroid_bt_app.hid.KeyCode;

public class KeyBoardActivity extends Activity implements OnClickListener {
  // Debugging
  private static final String TAG = "Catroid-BT";
  private static final boolean D = true;

  private Communicator com = null;
  // Intent request codes
  private static final int REQUEST_ENABLE_BT = 1;
  // MacAddress
  private String macaddress;
  // BT state view
  private TextView bt_state;
  Button shift,ctrl;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_key_board);
    com = new Communicator(BluetoothManager.getBluetoothManager(this));

    Button bt_connect = (Button) findViewById(R.id.button_bt_connect);
    bt_connect.setOnClickListener(this);

    bt_state = (TextView) findViewById(R.id.textView_bt_state);

    Button q = (Button) findViewById(R.id.button_q);
    q.setOnClickListener(this);
    Button w = (Button) findViewById(R.id.button_w);
    w.setOnClickListener(this);
    Button e = (Button) findViewById(R.id.button_e);
    e.setOnClickListener(this);
    Button r = (Button) findViewById(R.id.button_r);
    r.setOnClickListener(this);
    // Second row
    Button a = (Button) findViewById(R.id.button_a);
    a.setOnClickListener(this);
    Button s = (Button) findViewById(R.id.button_s);
    s.setOnClickListener(this);
    Button d = (Button) findViewById(R.id.button_d);
    d.setOnClickListener(this);
    Button space = (Button) findViewById(R.id.button_space);
    space.setOnClickListener(this);
    shift = (Button) findViewById(R.id.button_l_shift);
    shift.setOnClickListener(this);
    ctrl = (Button) findViewById(R.id.button_l_ctrl);
    ctrl.setOnClickListener(this);

    Log.i(TAG, "Key:Started Keyboard Activity");
  }

  public void onClick(View v) {
    Collection<KeyCode> keyList = new ArrayList<KeyCode>();
    if(shift.isPressed()) keyList.add(new KeyCode(true,225));
    if(ctrl.isPressed()) keyList.add(new KeyCode(true,224));
    
    switch (v.getId()) {
    case R.id.button_bt_connect:
      // Launch the DeviceListActivity to see devices and do scan
      if (D)
        Log.d(TAG, "Key:Started DeviceListActivity");
      Intent serverIntent = new Intent(this, DeviceListActivity.class);
      startActivityForResult(serverIntent, REQUEST_ENABLE_BT);
      bt_state.setText("Request Connecting..");
      break;
      
    case R.id.button_q:
      keyList.add(new KeyCode(false, 20));
      break;
    case R.id.button_w:
      keyList.add(new KeyCode(false, 26));
      break;
    case R.id.button_e:
      keyList.add(new KeyCode(false, 8));
      break;
    case R.id.button_r:
      keyList.add(new KeyCode(false, 21));
      break;
    // Second row
    case R.id.button_a:
      keyList.add(new KeyCode(false, 4));
      break;
    case R.id.button_s:
      keyList.add(new KeyCode(false, 22));
      break;
    case R.id.button_d:
      keyList.add(new KeyCode(false, 7));
      break;
    case R.id.button_space:
      keyList.add(new KeyCode(false, 44));
      break;
    }
    com.send(keyList);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (D)
      Log.d(TAG, "Key:onActivityResult " + resultCode);

    if (requestCode == REQUEST_ENABLE_BT) {
      // Get the device MAC address
      this.macaddress = data.getExtras().getString(
          DeviceListActivity.EXTRA_DEVICE_ADDRESS);
      bt_state.setText("Select " + macaddress);
      com.setMACAddress(macaddress);
      com.start();
      Log.i(TAG, "Key:Started Communicator");
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.key_board, menu);
    return true;
  }

}
