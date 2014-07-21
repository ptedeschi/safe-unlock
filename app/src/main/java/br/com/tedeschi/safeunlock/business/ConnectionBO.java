package br.com.tedeschi.safeunlock.business;

import android.content.Context;

import java.util.List;

import br.com.tedeschi.safeunlock.persistence.dao.ConnectionDao;
import br.com.tedeschi.safeunlock.persistence.dao.DaoManager;
import br.com.tedeschi.safeunlock.persistence.vo.Connection;

/**
 * Created by tedeschi on 7/20/14.
 */
public class ConnectionBO {
    /** Tag for logging operations */
    private static final String TAG = ConnectionBO.class.getSimpleName();

    /** Database manager */
    private DaoManager mDaoManager = null;

    /** Connection data access object */
    private ConnectionDao mConnectionDao = null;


    public ConnectionBO(Context context) {
        mDaoManager = DaoManager.getInstance(context);
        mConnectionDao = mDaoManager.getDaoSession().getConnectionDao();
    }

    public void insert(Connection connection) {
        mConnectionDao.insert(connection);
    }

    public void insertAll(List<Connection> connections) {
        mConnectionDao.insertInTx(connections);
    }

    public void update(Connection connection) {
        mConnectionDao.update(connection);
    }

    public List<Connection> getAll() {
        return mConnectionDao.loadAll();
    }

    public void remove(Connection connection) {
        mConnectionDao.delete(connection);
    }

    public boolean isSafe(String uniqueId) {
        List<Connection> list = mConnectionDao.loadAll();

        if (null != list) {
            for(Connection x:list) {
                if (x.getName().equals(uniqueId) && x.getChecked()) {
                    return true;
                }
            }
        }

        return false;
    }

    public long count() {
        return mConnectionDao.count();
    }
}
