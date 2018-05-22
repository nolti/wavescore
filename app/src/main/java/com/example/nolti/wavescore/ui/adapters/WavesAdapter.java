package com.example.nolti.wavescore.ui.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.nolti.wavescore.R;
import com.example.nolti.wavescore.models.Wave;

import java.util.ArrayList;

public class WavesAdapter extends ArrayAdapter<Wave> {

    private final Context context;
    private final ArrayList<Wave> waves;

    // 1
    public WavesAdapter(Context context, int textViewResourceId, ArrayList<Wave> waves) {
        super(context, textViewResourceId, waves);
        this.context = context;
        this.waves = waves;
    }
    // 2
    @Override
    public int getCount() {
        return waves.size();
    }
    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }
    // 4
    @Nullable
    @Override
    public Wave getItem(int position) {
        return null;
    }
    // 5 MODIFICAR!!!
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.view_list_item_wave, null);
        }
        final TextView score = v.findViewById(R.id.score);
        Wave w = waves.get(position);
        if (w != null) {
            //TextView score = new TextView(context);
            score.setText(String.valueOf(w.getScore()));
        }
        return v;
    }
}