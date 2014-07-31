package br.com.tedeschi.safeunlock.manager;

import android.app.KeyguardManager;
import android.content.Context;

/**
 * Created by patrick.tedeschi on 28/07/2014.
 */
public class KeyguardLockManager {
    private static KeyguardLockManager mInstance = null;
    private KeyguardManager mKeyguardManager = null;
    private KeyguardManager.KeyguardLock mKeyguardLock = null;

    private KeyguardLockManager() {

    }

    public static KeyguardLockManager getInstance() {
        if (null == mInstance) {
            mInstance = new KeyguardLockManager();
        }

        return mInstance;
    }

    public void initialize(Context context) {
        mKeyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
        mKeyguardLock = mKeyguardManager.newKeyguardLock(context.getPackageName());
    }

   public void lock() {
       if (!mKeyguardManager.inKeyguardRestrictedInputMode()) {
           mKeyguardLock.reenableKeyguard();
       }
   }

    public void unlock() {
        if (mKeyguardManager.inKeyguardRestrictedInputMode()) {
            mKeyguardLock.disableKeyguard();
        }
    }
}
