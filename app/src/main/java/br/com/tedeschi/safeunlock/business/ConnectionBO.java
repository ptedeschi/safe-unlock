package br.com.tedeschi.safeunlock.business;

import android.content.Context;
import android.util.Log;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import br.com.tedeschi.safeunlock.manager.NetworkManager;
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

    /** Application context. */
    private Context mContext = null;


    public ConnectionBO(Context context) {
        mContext = context;
        mDaoManager = DaoManager.getInstance(context);
        mConnectionDao = mDaoManager.getDaoSession().getConnectionDao();
    }

    public void insert(Connection connection) {
        mConnectionDao.insert(connection);
    }

    public void insertAll(List<Connection> connections) {
        if (null != connections) {
            for (Connection x : connections) {
                Log.d(TAG, "Inserting " + x.getName());
            }
        }

        mConnectionDao.insertInTx(connections);
    }

    public void update(Connection connection) {
        mConnectionDao.update(connection);
    }

    public List<Connection> getAll() {
        Collection<Connection> databaseNetworks = mConnectionDao.loadAll();
        Collection<Connection> configuredNetworks = NetworkManager.getConfiguredNetworks(mContext);

        if (null == databaseNetworks || databaseNetworks.size() <= 0) {
            Log.d(TAG, "Database is empty. Insert all configured networks");

            if (null != configuredNetworks && configuredNetworks.size() > 0) {
                insertAll((List) configuredNetworks);
            }
        } else if (null != databaseNetworks && null != configuredNetworks && configuredNetworks.size() >= 0) {
            // Check if configured networks added / removed elements
            for (Connection x : configuredNetworks) {
                if (exists(x)) {
                    x.setChecked(isSafe(x.getName()));
                }
            }

            removeAll((List) databaseNetworks);
            insertAll((List) configuredNetworks);
        }

        List<Connection> result = mConnectionDao.loadAll();

        if (null != result && result.size() > 0) {
            Collections.sort(result);
        }

        return result;
    }

    public void remove(Connection connection) {
        mConnectionDao.delete(connection);
    }

    public void removeAll(List<Connection> connections) {
        if (null != connections) {
            for (Connection x : connections) {
                Log.d(TAG, "Deleting " + x.getName());
            }
        }

        mConnectionDao.deleteInTx(connections);
    }

    public boolean isSafe(String uniqueId) {
        uniqueId = uniqueId.replace("\"", "");

        List<Connection> list = mConnectionDao.loadAll();

        if (null != list) {
            for (Connection x : list) {
                Log.d(TAG, "Safe Address: " + x.getAddress());
                Log.d(TAG, "Safe Checked: " + x.getChecked());

                if (x.getName().equals(uniqueId) && x.getChecked()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean exists(Connection connection) {
        String uniqueId = connection.getName();
        uniqueId = uniqueId.replace("\"", "");

        List<Connection> list = mConnectionDao.loadAll();

        if (null != list) {
            for (Connection x : list) {
                if (x.getName().equals(uniqueId)) {
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
