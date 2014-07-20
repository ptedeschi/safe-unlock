package br.com.tedeschi.safeunlock;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private ListView mListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(new ArrayAdapter<Hotspot>(this,
                android.R.layout.simple_list_item_multiple_choice, NetworkUtil.getConfiguredNetworks(this)));

        mListView.setItemsCanFocus(false);
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
                String message = String.format("SafeUnlock %s\n©2014 Tedeschi\ndeveloper@tedeschi.com.br\nhttp://www.tedeschi.com.br", Util.getVersion(this));

                SpannableString s = new SpannableString(message);
                Linkify.addLinks(s, Linkify.ALL);

                AlertDialog alertDialog = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT)
                        .setPositiveButton(android.R.string.ok, null)
                                //.setIcon(R.drawable.icon)
                        .setTitle("About")
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
