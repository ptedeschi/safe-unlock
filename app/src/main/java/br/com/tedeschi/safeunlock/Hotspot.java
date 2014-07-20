package br.com.tedeschi.safeunlock;

/**
 * Created by tedeschi on 7/19/14.
 */
public class Hotspot {
    private String ssid = null;
    private String bssid = null;

    public String getSSID() {
        return ssid;
    }

    public void setSSID(String ssid) {
        this.ssid = ssid;
    }

    public String getBSSID() {
        return bssid;
    }

    public void setBSSID(String bssid) {
        this.bssid = bssid;
    }

    @Override
    public String toString() {
        return ssid.replace("\"", "");
    }
}
