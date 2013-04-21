package com.example.catroid_bt_app.hid;

public interface WirelessManager {
  public void setMACAddress(String macaddress);
  public void getCommunicator();
  public void startCommunicator();
  public void stopCommunicator();
  public void sendMessage(byte[] msg);
  public byte[] recieveMessage();
}
