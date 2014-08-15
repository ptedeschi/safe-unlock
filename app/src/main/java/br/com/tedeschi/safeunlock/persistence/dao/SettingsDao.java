package br.com.tedeschi.safeunlock.persistence.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import br.com.tedeschi.safeunlock.persistence.vo.Settings;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table TB_SETTINGS.
*/
public class SettingsDao extends AbstractDao<Settings, Void> {

    public static final String TABLENAME = "TB_SETTINGS";

    /**
     * Properties of entity Settings.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Key = new Property(0, String.class, "key", false, "key");
        public final static Property Value = new Property(1, String.class, "value", false, "value");
    };


    public SettingsDao(DaoConfig config) {
        super(config);
    }
    
    public SettingsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'TB_SETTINGS' (" + //
                "'key' TEXT NOT NULL UNIQUE ," + // 0: key
                "'value' TEXT);"); // 1: value
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TB_SETTINGS'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Settings entity) {
        stmt.clearBindings();
        stmt.bindString(1, entity.getKey());
 
        String value = entity.getValue();
        if (value != null) {
            stmt.bindString(2, value);
        }
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public Settings readEntity(Cursor cursor, int offset) {
        Settings entity = new Settings( //
            cursor.getString(offset + 0), // key
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // value
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Settings entity, int offset) {
        entity.setKey(cursor.getString(offset + 0));
        entity.setValue(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(Settings entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(Settings entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
