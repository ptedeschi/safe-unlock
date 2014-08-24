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

        if (null == mKeyguardManager) {
            Log.d(TAG, "Initializing KeyguardManager");
            mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        }

        if (null == mPowerManager) {
            Log.d(TAG, "Initializing PowerManager");
            mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        }

        if (null == mKeyguardLock) {
            Log.d(TAG, "Initializing KeyguardLock");
            mKeyguardLock = mKeyguardManager.newKeyguardLock(context.getPackageName());
        }

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

        // Avoid lock if its already locked
        if (!isKeyguardShowing()) {
            if (null != mKeyguardLock) {
                if (!mPowerManager.isScreenOn()) {
                    mKeyguardManager.exitKeyguardSecurely(new KeyguardManager.OnKeyguardExitResult() {
                        @Override
                        public void onKeyguardExitResult(boolean success) {
                            Log.d(TAG, "Reenabling Keyguard (forced)");
                            mKeyguardLock.reenableKeyguard();
                        }
                    });
                } else {
                    Log.d(TAG, "Reenabling Keyguard");
                    mKeyguardLock.reenableKeyguard();
                }
            } else {
                Log.e(TAG, "Invalid KeyguardLock... Have you called initialize?");
            }
        } else {
            Log.d(TAG, "Already locked");
        }

        Log.d(TAG, "-lock");
    }

    public void unlock() {
        Log.d(TAG, "+unlock");

        // Avoid lock if its already locked
        if (!isKeyguardShowing()) {
            if (null != mKeyguardLock) {
                Log.d(TAG, "Disabling Keyguard");
                mKeyguardLock.disableKeyguard();
            } else {
                Log.e(TAG, "Invalid KeyguardLock... Have you called initialize?");
            }
        } else {
            Log.d(TAG, "Showing keyguard... Not unlocking to avoid the Home press issue");
        }

        Log.d(TAG, "-unlock");
    }
}
