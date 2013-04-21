/**
 *  Author : Gihan Karunarathne
 */

package com.example.catroid_bt_app.bluetooth;

import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.util.Log;

import com.example.catroid_bt_app.hid.WirelessManager;

public class BluetoothManager implements WirelessManager {
  //Debugging
  private static final String TAG = "BluetoothChat";
  private static final boolean D = true;
  
  // Blue-tooth status
	private static final int REQUEST_ENABLE_BT = 2000;
	public static final int BLUETOOTH_NOT_SUPPORTED = -1;
	public static final int BLUETOOTH_ALREADY_ON = 1;
	public static final int BLUETOOTH_ACTIVATING = 0;
	//
	private static final UUID SPP_UUID = UUID.fromString("04c6093b-0000-1000-8000-00805f9b34fb");
	private String macaddress = "";
	//Blue-tooth Adapter
	private BluetoothAdapter bluetoothAdapter;
	//Activity
	private Activity act;
	//RFCOMM communicator
	private RFCOMM rfCom = null;
	//Bluetooth Manager
  private static BluetoothManager btManager = null;


	private BluetoothManager(Activity activity) {
	  this.act = activity;
	  this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	  this.activateBluetooth();
	}
	
	public synchronized static BluetoothManager getBluetoothManager(Activity activity){
	  if(btManager == null){
	    btManager = new BluetoothManager(activity);
	  }
	  return btManager;
	}
	
	public void setMACAddress(String macaddress){
	  this.macaddress = macaddress;
	}

	public int activateBluetooth() {
		if (bluetoothAdapter == null) {
			return BLUETOOTH_NOT_SUPPORTED;// Device does not support Blue-tooth
		}
		if (!bluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			act.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			return BLUETOOTH_ACTIVATING;
		} else {
			//Log.d("TAG", "LOOP!!!!!");
			return BLUETOOTH_ALREADY_ON;
		}
	}
	
	private void ensureDiscoverable() {
    if(D) Log.d(TAG, "ensure discoverable");
    if (bluetoothAdapter.getScanMode() !=
        BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        act.startActivity(discoverableIntent);
    }
}

  @Override
  public void getCommunicator() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void startCommunicator() {
    if(macaddress != ""){
      this.startBTCommunicator(macaddress);
    }
  }
  
  private void startBTCommunicator(String macaddress){
    rfCom = new RFCOMM(bluetoothAdapter);
    rfCom.setMACAddress(macaddress);
    rfCom.setServiceUUID(SPP_UUID);
    rfCom.start();
  }
  
  public void stopCommunicator(){
    if (rfCom != null) {
      try {
        rfCom.destroyConnection();
      } catch (IOException e) {
        e.printStackTrace();
      }
      rfCom = null;
    }
  }

  @Override
  public void sendMessage(byte[] msg) {
    if (rfCom != null) {
      try {
        rfCom.sendMessage(msg);
      } catch (Exception e) {
        Log.d(TAG, e.getMessage());
      }
    }
  }

  @Override
  public byte[] recieveMessage() {
    // TODO Auto-generated method stub
    return null;
  }
}
