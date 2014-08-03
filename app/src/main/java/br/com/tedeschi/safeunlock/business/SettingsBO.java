package br.com.tedeschi.safeunlock.business;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tedeschi on 8/3/14.
 */
public class SettingsBO {
    /**
     * Tag for logging operations
     */
    private static final String TAG = SettingsBO.class.getSimpleName();

    private static final String PREFS_NAME = "PrefsFile";
    private static final String PREFS_ENABLED_KEY = "enabled";
    private Context mContext = null;

    public SettingsBO(Context context) {
        mContext = context;
    }

    public boolean isEnabled() {
        SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(PREFS_ENABLED_KEY, true);
    }

    public void setEnabled(boolean enabled) {
        SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(PREFS_ENABLED_KEY, enabled);
        editor.commit();
    }
}
