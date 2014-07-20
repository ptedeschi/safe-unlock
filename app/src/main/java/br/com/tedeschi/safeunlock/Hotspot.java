package br.com.tedeschi.safeunlock;

/**
 * Created by tedeschi on 7/19/14.
 */
public class Hotspot {
    private String ssid = null;
    private String bssid = null;
    private boolean checked = false;

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

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }
}
