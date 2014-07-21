package br.com.tedeschi.safeunlock.persistence.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

/**
 * Custom OpenHelper.
 *
 * @author Patrick Tedeschi
 */
public class CustomOpenHelper extends DaoMaster.OpenHelper {
    /** Log tag. */
    private static final String TAG = CustomOpenHelper.class.getSimpleName();

    /**
     * Public constructor.
     * 
     * @param context
     *            The application context.
     * @param name
     *            Database name.
     * @param factory
     *            Cursor factory.
     */
    public CustomOpenHelper(Context context, String name, CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "[onUpgrade] Detected upgrade from version " + oldVersion + " to " + newVersion);

        for (int i = oldVersion; i < newVersion; i++) {
            switch (i) {

            default:
                Log.d(TAG, "[onUpgrade] Unhandled version...");
                break;
            }
        }
    }
}
