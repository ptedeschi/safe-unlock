package br.com.tedeschi.safeunlock;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

        if (enable) {
            Log.d(TAG, "Keyguard reenabled");

            mKeyguardLock.reenableKeyguard();
            mKeyguardLock = null;

            Toast.makeText(context, "Keyguard reenabled", Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG, "Keyguard disabled");

            mKeyguardLock.disableKeyguard();

            Toast.makeText(context, "Keyguard disabled", Toast.LENGTH_SHORT).show();
        }

        Log.d(TAG, "-handleKeyguard");
    }
}
