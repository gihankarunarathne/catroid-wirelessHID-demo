package com.example.catroid_bt_app.ui;

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

public class KeyBoardActivity extends Activity implements OnClickListener{
//Debugging
 private static final String TAG = "BluetoothChat";
 private static final boolean D = true;
 
 private Communicator com = null;
 //Intent request codes
 private static final int REQUEST_ENABLE_BT = 1;
 // MacAddress
 private String macaddress;
 // BT state view
 private TextView bt_state;
 
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
  }
  
  public void onClick(View v){
    switch (v.getId()) {
   case R.id.button_bt_connect:
  // Launch the DeviceListActivity to see devices and do scan
     if(D) Log.d(TAG, "Started DeviceListActivity");
     Intent serverIntent = new Intent(this, DeviceListActivity.class);
     startActivityForResult(serverIntent, REQUEST_ENABLE_BT);
     bt_state.setText("Request Connecting..");
     break;
   case R.id.button_q :
     com.send("q");
     break;
   }
  }
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(D) Log.d(TAG, "onActivityResult " + resultCode);
    
    if(requestCode == REQUEST_ENABLE_BT){
      // Get the device MAC address
      this.macaddress = data.getExtras()
          .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
      bt_state.setText("Select "+macaddress);
      com.setMACAddress(macaddress);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.key_board, menu);
    return true;
  }

}
