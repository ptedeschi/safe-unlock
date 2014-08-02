package br.com.tedeschi.safeunlock.manager;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

/**
 * Created by patrick.tedeschi on 28/07/2014.
 */
public class KeyguardLockManager {
    private static final String TAG = KeyguardLockManager.class.getSimpleName();

    private static KeyguardLockManager mInstance = null;
    private KeyguardManager mKeyguardManager = null;
    private PowerManager mPowerManager = null;
    private KeyguardManager.KeyguardLock mKeyguardLock = null;

    private KeyguardLockManager() {
        Log.d(TAG, "+KeyguardLockManager");

        Log.d(TAG, "-KeyguardLockManager");
    }

    public static KeyguardLockManager getInstance() {
        if (null == mInstance) {
            mInstance = new KeyguardLockManager();
        }

        return mInstance;
    }

    public void initialize(Context context) {
        Log.d(TAG, "+initialize");

        Log.d(TAG, "Initializing KeyguardLock");

        mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mKeyguardLock = mKeyguardManager.newKeyguardLock(context.getPackageName());

        Log.d(TAG, "-initialize");
    }

    public boolean isKeyguardShowing() {
        Log.d(TAG, "+isKeyguardShowing");

        boolean result = false;

        if (null != mKeyguardManager) {
            result = mKeyguardManager.inKeyguardRestrictedInputMode();
        } else {
            Log.e(TAG, "Invalid KeyguardManager... Have you called initialize?");
        }

        Log.d(TAG, "-isKeyguardShowing: " + result);

        return result;
    }

    public void lock() {
        Log.d(TAG, "+lock");

        if (null != mKeyguardLock) {
            Log.d(TAG, "Reenabling Keyguard");

            if (!mPowerManager.isScreenOn()) {
                mKeyguardManager.exitKeyguardSecurely(new KeyguardManager.OnKeyguardExitResult() {
                    @Override
                    public void onKeyguardExitResult(boolean success) {
                        mKeyguardLock.reenableKeyguard();
                    }
                });
            } else {
                mKeyguardLock.reenableKeyguard();
            }
        } else {
            Log.e(TAG, "Invalid KeyguardLock... Have you called initialize?");
        }

        Log.d(TAG, "-lock");
    }

    public void unlock() {
        Log.d(TAG, "+unlock");

        if (null != mKeyguardLock) {
            Log.d(TAG, "Disabling Keyguard");

            mKeyguardLock.disableKeyguard();
        } else {
            Log.e(TAG, "Invalid KeyguardLock... Have you called initialize?");
        }

        Log.d(TAG, "-unlock");
    }
}
