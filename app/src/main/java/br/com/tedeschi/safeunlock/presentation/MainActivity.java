package br.com.tedeschi.safeunlock.presentation;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import br.com.tedeschi.safeunlock.R;
import br.com.tedeschi.safeunlock.Util;
import br.com.tedeschi.safeunlock.adapter.HotspotAdapter;
import br.com.tedeschi.safeunlock.business.ConnectionBO;
import br.com.tedeschi.safeunlock.manager.NetworkManager;
import br.com.tedeschi.safeunlock.persistence.vo.Connection;
import br.com.tedeschi.safeunlock.service.UnlockService;


public class MainActivity extends SherlockActivity {
    private ListView mListView = null;
    // private static final String FLURRY_API_KEY = "F2GBYND5V2RFTF3X4KVZ"; // Prod
    private static final String FLURRY_API_KEY = "VP8RKFCQ4D695SR75832"; // Dev

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectionBO connectionBO = new ConnectionBO(this);

        if (connectionBO.count() <= 0) {
            List<Connection> configuredNetworks = NetworkManager.getConfiguredNetworks(this);

            if (null != configuredNetworks && configuredNetworks.size() > 0) {
                connectionBO.insertAll(configuredNetworks);
            }
        }

        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(new HotspotAdapter(this, connectionBO.getAll()));

        mListView.setItemsCanFocus(false);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {
            AdView adView = (AdView) this.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice("485A638A8A6A15D3EA1FD2E659272FC3")
                    .addTestDevice("C3C40ED34A942DE4298EABB8EBF71D90")
                    .build();
            adView.loadAd(adRequest);
        }

        Intent service = new Intent(this, UnlockService.class);
        startService(service);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD_MR1) {
            FlurryAgent.onStartSession(this, FLURRY_API_KEY);
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
            case R.id.action_about:
                // Linkify the message
                String message = String.format("%s %s\n%s\n%s\n%s", getString(R.string.app_name), Util.getVersion(this), getString(R.string.app_copyright), getString(R.string.app_email), getString(R.string.app_site));

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
}
