package com.voltiosx.wavescore.ui.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.voltiosx.wavescore.R;
import com.voltiosx.wavescore.models.Rider;
import java.util.ArrayList;

public class HeatAdapter extends RecyclerView.Adapter<HeatAdapter.viewHolderRiders> implements View.OnClickListener{
    private final ArrayList<Rider> riders;
    private View.OnClickListener listener;

    public HeatAdapter(@NonNull ArrayList<Rider> riders) {
        this.riders = riders;
        // Actualiza al cambiar los datos
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    // 1
    public class viewHolderRiders extends RecyclerView.ViewHolder {
        Context context;
        CardView cardview;
        TextView tv_name, tv_hometown, tv_ranking, tv_status,
                tv_wave1, tv_wave2, tv_wave3, tv_wave4, tv_wave5, tv_wave6, tv_wave7, tv_wave8, tv_wave9, tv_wave10,
                hl_best_wave1, hl_best_wave2, hl_total_score, tv_best_wave1, tv_best_wave2, tv_total_score; //variables seteadas inicialmente

        public viewHolderRiders(View v) {
            super(v);
            context = v.getContext();

            cardview = v.findViewById(R.id.heat_cardview);
            tv_name = v.findViewById(R.id.tv_name);
            tv_hometown = v.findViewById(R.id.tv_hometown);
            tv_ranking = v.findViewById(R.id.tv_ranking);
            tv_status = v.findViewById(R.id.tv_status);
            tv_wave1 = v.findViewById(R.id.tv_wave1);
            tv_wave2 = v.findViewById(R.id.tv_wave2);
            tv_wave3 = v.findViewById(R.id.tv_wave3);
            tv_wave4 = v.findViewById(R.id.tv_wave4);
            tv_wave5 = v.findViewById(R.id.tv_wave5);
            tv_wave6 = v.findViewById(R.id.tv_wave6);
            tv_wave7 = v.findViewById(R.id.tv_wave7);
            tv_wave8 = v.findViewById(R.id.tv_wave8);
            tv_wave9 = v.findViewById(R.id.tv_wave9);
            tv_wave10 = v.findViewById(R.id.tv_wave10);
            hl_best_wave1 = v.findViewById(R.id.hl_best_wave1);
            hl_best_wave2 = v.findViewById(R.id.hl_best_wave2);
            hl_total_score = v.findViewById(R.id.hl_total_score);
            tv_best_wave1 = v.findViewById(R.id.tv_best_wave1);
            tv_best_wave2 = v.findViewById(R.id.tv_best_wave2);
            tv_total_score = v.findViewById(R.id.tv_total_score);
        }
    }

    // 2
    @NonNull
    @Override
    public viewHolderRiders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_list_item_heat, null, false);
        view.setOnClickListener(this);
        return new viewHolderRiders(view);
    }

    // 3
    @Override
    public void onBindViewHolder(@NonNull HeatAdapter.viewHolderRiders holder, int position) {
        Rider r = riders.get(position);
        int t =  r.getColors().get(0);

        /* SETEO DE COLORES */

        // rider data
        holder.cardview.setCardBackgroundColor(r.getColors().get(1));
        holder.tv_name.setTextColor(t);
        holder.tv_hometown.setTextColor(t);
        holder.tv_ranking.setTextColor(t);
        holder.tv_status.setTextColor(t);

        // score data
        holder.tv_wave1.setTextColor(t);
        holder.tv_wave2.setTextColor(t);
        holder.tv_wave3.setTextColor(t);
        holder.tv_wave4.setTextColor(t);
        holder.tv_wave5.setTextColor(t);
        holder.tv_wave6.setTextColor(t);
        holder.tv_wave7.setTextColor(t);
        holder.tv_wave8.setTextColor(t);
        holder.tv_wave9.setTextColor(t);
        holder.tv_wave10.setTextColor(t);
        holder.hl_best_wave1.setTextColor(t);
        holder.hl_best_wave2.setTextColor(t);
        holder.hl_total_score.setTextColor(t);
        holder.tv_best_wave1.setTextColor(t);
        holder.tv_best_wave2.setTextColor(t);
        holder.tv_total_score.setTextColor(t);

        /* SETEO DE DATOS */

        // rider data
        holder.tv_name.setText(r.getName());
        holder.tv_hometown.setText(r.getHometown());
        holder.tv_status.setText(r.getHeatstatus());

        // waves data
        holder.tv_wave1.setText(String.valueOf(r.getWavestaken().get(0)));
        holder.tv_wave2.setText(String.valueOf(r.getWavestaken().get(1)));
        holder.tv_wave3.setText(String.valueOf(r.getWavestaken().get(2)));
        holder.tv_wave4.setText(String.valueOf(r.getWavestaken().get(3)));
        holder.tv_wave5.setText(String.valueOf(r.getWavestaken().get(4)));
        holder.tv_wave6.setText(String.valueOf(r.getWavestaken().get(5)));
        holder.tv_wave7.setText(String.valueOf(r.getWavestaken().get(6)));
        holder.tv_wave8.setText(String.valueOf(r.getWavestaken().get(7)));
        holder.tv_wave9.setText(String.valueOf(r.getWavestaken().get(8)));
        holder.tv_wave10.setText(String.valueOf(r.getWavestaken().get(9)));

        // average data
        holder.tv_best_wave1.setText(String.valueOf(r.getSortwavestaken().get(0)));
        holder.tv_best_wave2.setText(String.valueOf(r.getSortwavestaken().get(1)));
        holder.tv_total_score.setText(String.valueOf(r.getHeatscores().get(0)));

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return riders.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
}