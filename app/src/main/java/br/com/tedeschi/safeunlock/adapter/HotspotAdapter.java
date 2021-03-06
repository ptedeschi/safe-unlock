package br.com.tedeschi.safeunlock.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import br.com.tedeschi.safeunlock.R;
import br.com.tedeschi.safeunlock.persistence.vo.Connection;

/**
 * Custom adapter for displaying an array of Connection objects.
 */
public class HotspotAdapter extends ArrayAdapter<Connection> {

    private LayoutInflater inflater;

    private Context mContext = null;

    private CheckBoxListener mListener = null;

    public HotspotAdapter(Context context, List<Connection> connectionList) {
        super(context, R.layout.list_row, connectionList);

        // Cache the LayoutInflate to avoid asking for a new one each time.
        inflater = LayoutInflater.from(context);

        mContext = context;
    }

    public void setListener(CheckBoxListener listener) {
        mListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Connection to display
        Connection connection = (Connection) this.getItem(position);

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

            // If CheckBox is toggled, update the connection it is tagged with.
            checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (null != mListener) {
                        CheckBox checkBox = (CheckBox) v;
                        Connection connection = (Connection) checkBox.getTag();

                        mListener.onCheckBoxToggled(connection, checkBox.isChecked());
                    }
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

        // Tag the CheckBox with the Connection it is displaying, so that we can
        // access the connect in onClick() when the CheckBox is toggled.
        checkBox.setTag(connection);

        // Display connection data
        checkBox.setChecked(connection.getChecked());
        textView.setText(connection.getName().replace("\"", ""));

        return convertView;
    }

    /**
     * Adds the specified Collection at the end of the array.
     * @param collection The Collection to add at the end of the array.
     */
    @SuppressLint("NewApi")
    public void addAllEx(List<Connection> collection) {
        if (null != collection && collection.size() > 0) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                addAll(collection);
            } else {
                for (Connection conn : collection) {
                    add(conn);
                }
            }
        }
    }

    public interface CheckBoxListener {

        public void onCheckBoxToggled(Connection conecction, boolean checked);
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
