package br.com.tedeschi.safeunlock.presentation;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import br.com.tedeschi.safeunlock.DeviceAdmin;
import br.com.tedeschi.safeunlock.NetworkUtil;
import br.com.tedeschi.safeunlock.R;
import br.com.tedeschi.safeunlock.Util;
import br.com.tedeschi.safeunlock.adapter.HotspotAdapter;
import br.com.tedeschi.safeunlock.business.ConnectionBO;
import br.com.tedeschi.safeunlock.service.UnlockService;


public class MainActivity extends SherlockActivity {
    private ListView mListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Device Policy Manager service and our receiver class
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(this, DeviceAdmin.class);

        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Your boss told you to do this");
        startActivity(intent);

        ConnectionBO connectionBO = new ConnectionBO(this);

        if (connectionBO.count() <= 0) {
            connectionBO.insertAll(NetworkUtil.getConfiguredNetworks(this));
        }

        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(new HotspotAdapter(this, connectionBO.getAll()));

        mListView.setItemsCanFocus(false);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        AdView adView = (AdView) this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("485A638A8A6A15D3EA1FD2E659272FC3")
                .addTestDevice("C3C40ED34A942DE4298EABB8EBF71D90")
                .build();
        adView.loadAd(adRequest);

        Intent service = new Intent(this, UnlockService.class);
        startService(service);
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
