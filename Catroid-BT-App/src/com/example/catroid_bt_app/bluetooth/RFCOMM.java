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
    private static final boolean D = false;
    // Blue-tooth Access
    private BluetoothAdapter btAdapter;
    private BluetoothSocket mmSocket = null;
    private InputStream mmInStream = null;
    private OutputStream mmOutStream = null;

    private static Queue<byte[]> messageQueue = new LinkedList<byte[]>();

    private String macAddress;
    private UUID serviceUUID;

    protected byte[] returnMessage;

    protected boolean isConnected = false;

    public RFCOMM(BluetoothAdapter btAdapter) {
        this.btAdapter = btAdapter;
    }

    public void setMACAddress(String mMACaddress) {
        this.macAddress = mMACaddress;
    }

    public void setServiceUUID(UUID serviceUUID) {
        this.serviceUUID = serviceUUID;
    }

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

        while (isConnected) {
            try {
                receiveMessage();
                try {
                    Thread.sleep(10);
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
                Log.i(TAG, "RFCOMM:Start creating.. with MAC:" + macAddress
                        + " UUID:" + serviceUUID);
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
                    Method mMethod = btDevice.getClass().getMethod(
                            "createRfcommSocket", new Class[] { int.class });
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
            isConnected = mmSocket.isConnected();
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
                isConnected = false;
                mmSocket.close();
                mmSocket = null;
            }
            mmOutStream = null;
            mmInStream = null;
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        } finally {
            this.isConnected = false;
        }
    }

    public synchronized void sendMessage(byte[] bytes) {
        if (mmSocket.isConnected()) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Log.w(TAG, "Bluetooth Disconnected. " + e.getMessage());
            }
        } else {
            try {
                this.createConnection(this.macAddress, this.serviceUUID);
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }
    }

    public synchronized byte[] receiveMessage() throws IOException {
        if (mmInStream == null) {
            throw new IOException();
        }

        long length = mmInStream.available();
        byte[] bytes = new byte[(int) length];
        mmInStream.read(bytes);

        if (D) {
            Log.i("--bt", "" + bytes.length);

            if (length >= 5) {
                Log.i(TAG, "" + (int) bytes[4]);
                messageQueue.add(bytes);
                byte[] a = messageQueue.peek();
                Log.i(TAG, "bytes length: " + a.length);
            }
        }
        return bytes;
    }

}