package br.com.tedeschi.safeunlock.presentation;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.flurry.android.FlurryAgent;

import org.jraf.android.backport.switchwidget.Switch;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.tedeschi.safeunlock.Constants;
import br.com.tedeschi.safeunlock.R;
import br.com.tedeschi.safeunlock.Util;
import br.com.tedeschi.safeunlock.adapter.HotspotAdapter;
import br.com.tedeschi.safeunlock.adapter.HotspotAdapter.CheckBoxListener;
import br.com.tedeschi.safeunlock.business.ConnectionBO;
import br.com.tedeschi.safeunlock.business.LockBO;
import br.com.tedeschi.safeunlock.business.SettingsBO;
import br.com.tedeschi.safeunlock.persistence.vo.Connection;


public class MainActivity extends SherlockActivity implements CheckBoxListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Switch mSwitch = null;

    private ListView mListView = null;

    private HotspotAdapter mHotspotAdapter = null;

    private TextView mWarning = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwitch = (Switch) findViewById(R.id.switch_enable);
        mWarning = (TextView) findViewById(R.id.textView_warning);

        final SettingsBO settingsBO = new SettingsBO(this);
        mSwitch.setChecked(settingsBO.isEnabled());
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                settingsBO.setEnabled(checked);

                LockBO.handleChange(MainActivity.this);
            }
        });

        // Get all connection items and handle visibility of warning if needed
        ConnectionBO connectionBO = new ConnectionBO(this);
        List<Connection> list = connectionBO.getAll();

        if (null == list || list.size() <= 0) {
            Log.e(TAG, "There's no configured networks");
            mWarning.setVisibility(View.VISIBLE);
        } else {
            mWarning.setVisibility(View.GONE);
        }

        mHotspotAdapter = new HotspotAdapter(this, list);
        mHotspotAdapter.setListener(this);

        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(mHotspotAdapter);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {
            AdView adView = (AdView) this.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice("485A638A8A6A15D3EA1FD2E659272FC3")
                    .build();
            adView.loadAd(adRequest);
        }

        LockBO.handleChange(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD_MR1) {
            FlurryAgent.onStartSession(this, getString(R.string.flurry_api_key));
            FlurryAgent.setCaptureUncaughtExceptions(true);
            FlurryAgent.setLogEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD_MR1) {
            FlurryAgent.onEndSession(this);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh:
                // Get all connection items and handle visibility of warning if needed
                ConnectionBO connectionBO = new ConnectionBO(this);
                List<Connection> list = connectionBO.getAll();

                if (null == list || list.size() <= 0) {
                    Log.e(TAG, "There's no configured networks");
                    mWarning.setVisibility(View.VISIBLE);
                } else {
                    mWarning.setVisibility(View.GONE);
                }

                mHotspotAdapter.clear();
                mHotspotAdapter.addAllEx(list);
                break;

            case R.id.action_about:
                // Linkify the message
                String message = String.format("%s\nver. %s\n%s\n%s", getString(R.string.app_name), Util.getVersion(this), getString(R.string.app_copyright), getString(R.string.app_email));

                SpannableString s = new SpannableString(message);
                Linkify.addLinks(s, Linkify.ALL);

                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setPositiveButton(android.R.string.ok, null)
                        .setTitle(getString(R.string.dialog_about_title))
                        .setMessage(s)
                        .create();

                alertDialog.show();

                // Make the textview clickable. Must be called after show()
                ((TextView) alertDialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
                break;
            case R.id.action_share:
                Util.share(this);

                break;
            case R.id.action_rate:
                Util.rate(this);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckBoxToggled(Connection conecction, boolean checked) {
        Log.d(TAG, "Connection checkbox changed: " + conecction.getName() + " -> " + checked);

        conecction.setChecked(checked);

        ConnectionBO connectionBO = new ConnectionBO(this);
        connectionBO.update(conecction);

        // Notify changes
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_SAFE_CHANGED);
        sendBroadcast(intent);
    }
}
