package br.com.tedeschi.safeunlock.persistence.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Dao manager.
 *
 * @author Patrick Tedeschi
 */
public class DaoManager {
    /**
     * DaoManager singletons instance
     */
    private static DaoManager sInstance = null;
    /**
     * Database reference
     */
    private SQLiteDatabase mDatabase = null;
    /**
     * DaoMaster reference
     */
    private DaoMaster mDaoMaster = null;
    /**
     * DaoSession reference
     */
    private DaoSession mDaoSession = null;
    /**
     * Database helper
     */
    private SQLiteOpenHelper mHelper = null;
    /**
     * Bookstore database name
     */
    private static final String DATABASE_NAME = "database.db";

    /**
     * Constructor.
     *
     * @param context The application context.
     */
    private DaoManager(Context context) {
        mHelper = new CustomOpenHelper(context, DATABASE_NAME, null);
        mDatabase = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mDatabase);
        mDaoSession = mDaoMaster.newSession();
    }

    /**
     * Get DaoManager instance (Singleton).
     *
     * @param context The application context.
     * @return The DaoManager created instance.
     */
    public static DaoManager getInstance(Context context) {
        if (null == sInstance) {
            sInstance = new DaoManager(context);
        }
        return sInstance;
    }

    /**
     * Returns the DaoMaster {@link DaoMaster} instance.
     *
     * @return The DaoMaster {@link DaoMaster} instance.
     */
    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    /**
     * Returns the DaoSession {@link DaoSession} instance.
     *
     * @return The DaoSession {@link DaoSession} instance.
     */
    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    /**
     * Return the database helper.
     *
     * @return The database helper reference.
     */
    public SQLiteOpenHelper getOpenHelper() {
        return mHelper;
    }

    /**
     * Clears the DaoSession cache and close the database.
     */
    public void close() {
        if (null != mDaoSession) {
            mDaoSession.clear();
        }

        if (null != mDatabase) {
            mDatabase.close();
        }

        sInstance = null;
    }
}
