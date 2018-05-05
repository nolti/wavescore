package com.voltiosx.nolti.wavescore.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.voltiosx.nolti.wavescore.R;
import com.voltiosx.nolti.wavescore.models.Rider;
import java.util.ArrayList;

public class RidersAdapter extends ArrayAdapter<Rider> {

    Context context;
    private ArrayList<Rider> riders;

    public RidersAdapter(Context context, int textViewResourceId, ArrayList<Rider> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.riders = items;
    }

    @Override
    public View getView(int cont, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // Asocio el LAYOUT rider.xml
            v = vi.inflate(R.layout.view_list_item_rider, null);
        }
        Rider o = riders.get(cont);
        if (o != null) {
            // references
            TextView pos = v.findViewById(R.id.position);
            TextView nom = v.findViewById(R.id.name);
            TextView cat = v.findViewById(R.id.category);
            // set values
            pos.setText(String.valueOf(o.getPosition()));
            nom.setText(String.valueOf(o.getName()));
            cat.setText(String.valueOf(o.getCategoria()));
        }
        return v;
    }
}