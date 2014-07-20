package br.com.tedeschi.safeunlock;

import android.content.Context;
import android.util.Log;

/**
 * Created by tedeschi on 7/19/14.
 */
public class KeyguardManager {
    private static final String TAG = KeyguardManager.class.getSimpleName();

    private static android.app.KeyguardManager.KeyguardLock mKeyguardLock = null;

    private static KeyguardManager mInstance = null;

    private KeyguardManager() {
        Log.d(TAG, "+KeyguardManager constructor");

        Log.d(TAG, "-KeyguardManager constructor");
    }

    public static KeyguardManager getInstance() {
        if (null == mInstance) {
            mInstance = new KeyguardManager();
        }

        return mInstance;
    }

    public void enable(Context context) {
        handleKeyguard(context, true);
    }

    public void disable(Context context) {
        handleKeyguard(context, false);
    }

    @SuppressWarnings("deprecation")
    private void handleKeyguard(Context context, boolean enable) {
        Log.d(TAG, "+handleKeyguard: " + enable);

        if (mKeyguardLock == null) {
            Log.d(TAG, "mKeyguardLock is null. Creating a new one");

            android.app.KeyguardManager km = (android.app.KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            mKeyguardLock = km.newKeyguardLock("MyKeyguardLock");
        }

        try {
            Log.d(TAG, "Reenable before disabling for safety");

            mKeyguardLock.reenableKeyguard();
        } catch (Exception e) {
            Log.e(TAG, "Probably already reenabled: " + e.getMessage());
        }

        if (!enable) {
            Log.d(TAG, "Disabling keyguard");

            mKeyguardLock.disableKeyguard();
        }

        Log.d(TAG, "-handleKeyguard");
    }
}
