package com.example.catroid_bt_app.hid;

public class Communicator {
  private WirelessManager hid;
  
  public Communicator(WirelessManager hid){
    this.hid = hid;
  }
  
  public void setMACAddress(String macaddress){
    hid.setMACAddress(macaddress);
  }
  
  public void start(){
    hid.startCommunicator();
  }
  
  public void stop(){
    hid.stopCommunicator();
  }
  
  public void send(String msg){
    hid.sendMessage(msg.getBytes());
  }
  
}
