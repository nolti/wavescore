package com.voltiosx.wavescore.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.voltiosx.wavescore.R;
import com.voltiosx.wavescore.models.Rider;
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
    public View getView(int pos, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // Asocio el LAYOUT rider.xml
            v = vi.inflate(R.layout.view_list_item_rider, null);
        }
        Rider rider = riders.get(pos);
        if (rider != null) {
            // references
            LinearLayout row = v.findViewById(R.id.row);
            TextView position = v.findViewById(R.id.position);
            TextView name = v.findViewById(R.id.name);
            TextView category = v.findViewById(R.id.category);

            row.setBackgroundColor(ContextCompat.getColor(context, R.color.inactive));
            String categoria = rider.getCategoria();
            int textLight = ContextCompat.getColor(context, R.color.colorTextLight);
            int textDark = ContextCompat.getColor(context, R.color.colorTextDark);
            int selectColor;
            // colors
            switch(categoria) {
                case "OPEN PRO":
                    selectColor = ContextCompat.getColor(context, R.color.monokai_blue);
                    break;
                case "MASTERS":
                    selectColor = ContextCompat.getColor(context, R.color.monokai_emerald);
                    break;
                case "OPEN PRO, MASTERS":
                    selectColor = ContextCompat.getColor(context, R.color.monokai_emerald);
                    break;
                case "DK PRO":
                    selectColor = ContextCompat.getColor(context, R.color.monokai_green);
                    break;
                case "DAMAS":
                    selectColor = ContextCompat.getColor(context, R.color.monokai_pink);
                    break;
                case "AMATEURS":
                    selectColor = ContextCompat.getColor(context, R.color.monokai_lila);
                    break;
                case "MENORES DE 18":
                    selectColor = ContextCompat.getColor(context, R.color.monokai_ornge);
                    break;
                case "MENORES DE 16":
                    selectColor = ContextCompat.getColor(context, R.color.monokai_yellow);
                    break;
                case "MENORES DE 14":
                    selectColor = ContextCompat.getColor(context, R.color.monokai_fg1);
                    break;
                case "MENORES DE 12":
                    selectColor = ContextCompat.getColor(context, R.color.monokai_fg0);
                    break;
                default:
                    selectColor = textLight;
            } // end switch
            position.setTextColor(selectColor);
            name.setTextColor(selectColor);
            category.setTextColor(selectColor);

            // set values
            position.setText(String.valueOf(rider.getPosition()));
            name.setText(String.valueOf(rider.getName()));
            category.setText(categoria);
        }
        return v;
    }
}