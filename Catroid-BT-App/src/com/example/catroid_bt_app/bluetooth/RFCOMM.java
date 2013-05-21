/**
 *  Author : Gihan Karunarathne
 */
package com.example.catroid_bt_app.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class RFCOMM extends Thread {
  // Debugging
  private static final String TAG = "Catroid-BT";
  private static final boolean D = true;
  // Blue-tooth Access
  private BluetoothAdapter btAdapter;
  private BluetoothSocket mmSocket = null;
  private InputStream mmInStream = null;
  private OutputStream mmOutStream = null;

  private static Queue<byte[]> messageQueue = new LinkedList<byte[]>();

  private String macAddress;
  private UUID serviceUUID;

  protected byte[] returnMessage;

  protected boolean connected = false;

  /**
   * Public constructor
   * 
   * @param socket
   *          Blue-tooth Socket
   * @param btAdapter
   *          Blue-tooth Adapter
   */
  public RFCOMM(BluetoothAdapter btAdapter) {
    this.btAdapter = btAdapter;
  }

  /**
   * Set device MACAddress
   * 
   * @param mMACaddress
   *          MacAddress to be set
   */
  public void setMACAddress(String mMACaddress) {
    this.macAddress = mMACaddress;
  }

  /**
   * Set Service UUID
   * 
   * @param serviceUUID
   *          Service UUID to be set
   */
  public void setServiceUUID(UUID serviceUUID) {
    this.serviceUUID = serviceUUID;
  }

  /**
   * Check the socket state
   * 
   * @return boolean if connected send true, otherwise false
   */
  public boolean isConnected() {
    return this.mmSocket.isConnected();
  }

  /**
   * Creates the connection, waits for incoming messages and dispatches them.
   * The thread will be terminated on closing of the connection.
   */
  @Override
  public void run() {

    try {
      createConnection(macAddress, serviceUUID);
    } catch (IOException e) {
    }

    while (connected) {
      try {
        receiveMessage();
        try {
          Thread.sleep(40);
        } catch (InterruptedException e) {
          Log.e(TAG, "RFCOMM:unable to halt..", e);
        }
      } catch (IOException e) {
        return;
      }
    }
  }

  public void createConnection(String macAddress, UUID serviceUUID)
      throws IOException {
    try {
      if (D)
        Log.i(TAG, "RFCOMM:Start creating.. with MAC:" + macAddress + " UUID:"
            + serviceUUID);
      BluetoothSocket tmpBTSock;
      BluetoothDevice btDevice = null;
      btDevice = btAdapter.getRemoteDevice(macAddress);
      if (btDevice == null) {
        Log.d(TAG, "No remote device found");
      }
      tmpBTSock = btDevice.createRfcommSocketToServiceRecord(serviceUUID);
      try {
        tmpBTSock.connect();
      } catch (IOException e) {
        try {
          Method mMethod = btDevice.getClass().getMethod("createRfcommSocket",
              new Class[] { int.class });
          tmpBTSock = (BluetoothSocket) mMethod.invoke(btDevice,
              Integer.valueOf(1));
          tmpBTSock.connect();
        } catch (Exception e1) {
          Log.d(TAG, e1.getMessage());
          return;
        }
      }
      mmSocket = tmpBTSock;
      mmInStream = mmSocket.getInputStream();
      mmOutStream = mmSocket.getOutputStream();
      connected = mmSocket.isConnected();
      if (D)
        Log.i(TAG, "Created socket. State: " + mmSocket.isConnected());
    } catch (IOException e) {
      Log.d(TAG, e.getMessage());
    }
  }

  public void destroyConnection() throws IOException {
    try {
      byte[] exit = { -1 };

      if (mmSocket != null && mmOutStream != null) {
        this.mmOutStream.write(exit);
      }
      if (mmSocket != null) {
        connected = false;
        mmSocket.close();
        mmSocket = null;
      }
      mmOutStream = null;
      mmInStream = null;
    } catch (IOException e) {
      Log.d(TAG, e.getMessage());
    }
  }

  /**
   * Send message to PC side
   * 
   * @param bytes
   *          Message to be send
   */
  public synchronized void sendMessage(byte[] bytes) {
    if (mmSocket.isConnected()) {
      try {
        mmOutStream.write(bytes);
        Log.i(TAG, "RFCOMM:Send msg:" + bytes.toString());
      } catch (IOException e) {
        Log.w(TAG, "Bluetooth Disconnected. " + e.getMessage());
      }
    }
  }

  /**
   * Read data
   * 
   * @return return received message
   * @throws IOException
   */
  public synchronized byte[] receiveMessage() throws IOException {
    if (mmInStream == null) {
      throw new IOException();
    }

    long length = mmInStream.available();
    byte[] bytes = new byte[(int) length];
    mmInStream.read(bytes);

    Log.i("--bt", "" + bytes.length);

    if (length >= 5) {
      Log.i("bt", "" + (int) bytes[4]);
      messageQueue.add(bytes);
      byte[] a = messageQueue.peek();
      System.out.println("bytes length: " + a.length);
    }
    return bytes;
  }

  public String bytesToString(byte[] msg) {
    // construct a string from the buffer
    return new String(msg);
  }
}