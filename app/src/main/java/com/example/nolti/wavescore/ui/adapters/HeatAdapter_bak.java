package com.example.nolti.wavescore.ui.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nolti.wavescore.R;
import com.example.nolti.wavescore.models.Rider;

import java.util.ArrayList;

public class HeatAdapter_bak extends ArrayAdapter<Rider> implements View.OnClickListener {
    private final Context context;
    private final ArrayList<Rider> riders;
    private View.OnClickListener listener;

    public HeatAdapter_bak(@NonNull Context context, int textViewResourceId, @NonNull ArrayList<Rider> riders) {
        super(context, textViewResourceId, riders);
        this.context=context;
        this.riders=riders;
    }

    @Override
    public int getCount() { return riders.size(); }

    @Nullable
    @Override
    public Rider getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vista = convertView;
        if (vista==null){
            LayoutInflater inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vista = inflador.inflate(R.layout.view_card_list_item_heat, null);
        }
        String name, hometown, ranking, bestwave1, bestwave2, totalscore;
        Rider r=riders.get(position);
        name = r.getName();
        hometown = r.getHometown();

        TextView tv_name, tv_hometown, tv_ranking, tv_bestwave1, tv_bestwave2, tv_totalscore; //variables seteadas inicialmente
        tv_name = vista.findViewById(R.id.tv_name);
        tv_hometown = vista.findViewById(R.id.tv_hometown);
        tv_ranking = vista.findViewById(R.id.tv_ranking);
        tv_bestwave1 = vista.findViewById(R.id.tv_best_wave1);
        tv_bestwave2 = vista.findViewById(R.id.tv_best_wave2);
        tv_totalscore = vista.findViewById(R.id.tv_total_score);
        tv_name.setText(name);
        tv_hometown.setText(hometown);

        vista.setOnClickListener(this);

        return vista;
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }
}
