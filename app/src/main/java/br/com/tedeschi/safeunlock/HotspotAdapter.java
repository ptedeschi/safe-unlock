package br.com.tedeschi.safeunlock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Custom adapter for displaying an array of Planet objects.
 */
public class HotspotAdapter extends ArrayAdapter<Hotspot> {

    private LayoutInflater inflater;

    public HotspotAdapter(Context context, List<Hotspot> planetList) {
        super(context, R.layout.list_row, planetList);

        // Cache the LayoutInflate to avoid asking for a new one each time.
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Planet to display
        Hotspot planet = (Hotspot) this.getItem(position);

        // The child views in each row.
        CheckBox checkBox;
        TextView textView;

        // Create a new row view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row, null);

            // Find the child views.
            textView = (TextView) convertView.findViewById(R.id.textView);
            checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

            // Optimization: Tag the row with it's child views, so we don't have to
            // call findViewById() later when we reuse the row.
            convertView.setTag(new HotspotViewHolder(textView, checkBox));

            // If CheckBox is toggled, update the planet it is tagged with.
            checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    Hotspot planet = (Hotspot) cb.getTag();
                    planet.setChecked(cb.isChecked());
                }
            });
        }
        // Reuse existing row view
        else {
            // Because we use a ViewHolder, we avoid having to call findViewById().
            HotspotViewHolder viewHolder = (HotspotViewHolder) convertView.getTag();
            checkBox = viewHolder.getCheckBox();
            textView = viewHolder.getTextView();
        }

        // Tag the CheckBox with the Planet it is displaying, so that we can
        // access the planet in onClick() when the CheckBox is toggled.
        checkBox.setTag(planet);

        // Display planet data
        checkBox.setChecked(planet.isChecked());
        textView.setText(planet.getSSID().replace("\"", ""));

        return convertView;
    }

}

/**
 * Holds child views for one row.
 */
class HotspotViewHolder {
    private CheckBox checkBox;
    private TextView textView;

    public HotspotViewHolder() {
    }

    public HotspotViewHolder(TextView textView, CheckBox checkBox) {
        this.checkBox = checkBox;
        this.textView = textView;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}