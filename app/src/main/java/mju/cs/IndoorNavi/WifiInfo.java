package mju.cs.IndoorNavi;

/**
 * Created by Joguk on 2016-04-26.
 */

public class WifiInfo {
    String ssid;
    int rssi;
    double avg; // rssi의 평균
    public String getSSID(){ return this.ssid; }
    public void setSSID(String ssid){ this.ssid = ssid;}
    public double getRSSI(){ return this.rssi; }
    public void setRSSI(int rssi){ this.rssi = rssi;}
    public double getAvg(){ return this.avg; }
    public void setAvg(double avg){ this.avg = avg;}

    public WifiInfo(){
        this.ssid = "1";
        this.rssi = 0;
        this.avg = 0;
    }

    public WifiInfo(String ssid, int rssi){x
        this.ssid = ssid;
        this.rssi = rssi;
    }
}
