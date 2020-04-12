package com.voltiosx.wavescore.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.voltiosx.wavescore.R;
import com.voltiosx.wavescore.io.FirestoreRecyclerClickInterface;
import com.voltiosx.wavescore.models.Tournament;
import java.text.DecimalFormat;

// clase Adaptador
public class TournamentAdapter extends FirestoreRecyclerAdapter<Tournament, TournamentAdapter.TournamentHolder> { // implements View.OnClickListener

    private View.OnClickListener listener;
    private DecimalFormat currency = new DecimalFormat("$###,###.##");
    public TextView tv_register;

    // Interface
    private FirestoreRecyclerClickInterface firestoreRecyclerClickInterface;

    public TournamentAdapter(@NonNull FirestoreRecyclerOptions<Tournament> options, FirestoreRecyclerClickInterface firestoreRecyclerClickInterface) { // envio como parametro la interface
        super(options);
        this.firestoreRecyclerClickInterface = firestoreRecyclerClickInterface; // inicializo la interfaz dentro del metodo de la clase TournamentAdapter
    }

    public interface setOnClickListener {
        void onItemClick(int position);
        void onRegisterClick(int position);
    }

    /*@Override
    public void onClick(View v) { }*/

    // clase Contenedor
    class TournamentHolder extends RecyclerView.ViewHolder {
        Context context;

        // Define views
        CardView cardview;
        ImageView iv_image;
        TextView tv_name, tv_location, tv_dateini, tv_status, tv_rating, tv_pricemoney, tv_description, tv_categorys;

        public TournamentHolder(@NonNull View v) {
            super(v);
            context = v.getContext();

            // Enlazo las vistas
            cardview = v.findViewById(R.id.heat_cardview);
            iv_image = v.findViewById(R.id.image_tournament);
            tv_name = v.findViewById(R.id.tv_name);
            tv_location = v.findViewById(R.id.tv_location);
            //tv_dateini = v.findViewById(R.id.tv_dateini);
            tv_status = v.findViewById(R.id.tv_status);
            //tv_rating = v.findViewById(R.id.tv_rating);
            tv_pricemoney = v.findViewById(R.id.tv_pricemoney);
            //tv_description = v.findViewById(R.id.tv_description);
            //tv_categorys = v.findViewById(R.id.tv_categorys);
            tv_register = v.findViewById(R.id.tv_register);

            tv_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firestoreRecyclerClickInterface.onItemClick(getAdapterPosition());
                }
            });

            /*v.setOnLongClickListener((view) -> {
                firestoreRecyclerClickInterface.onLongItemClick(getAdapterPosition());
                return true;
            });*/

            /*v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION) {
                            //listener2.onItemClick(position);
                            Log.d("TORNEO", "onClick: "+position);
                        }
                    }
                }
            });*/

            /*tv_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION) {
                            Log.d("TORNEO", "onClick register: "+position);
                            //listener2.onRegisterClick(position);
                        }
                    }
                }
            });*/
        }

        /*@Override
        public void onClick(View v) {
            if (listener!=null){
                listener.onClick(v);
            }
        }*/
    }

    @Override
    protected void onBindViewHolder(@NonNull TournamentHolder holder, int position, @NonNull Tournament model) {
        String pricemoney = currency.format(model.getPricemoney());
        //String pricemoney = "$"+model.getPricemoney().toString();
        holder.tv_status.setText(model.getStatus());
        holder.tv_pricemoney.setText(pricemoney);
        holder.tv_name.setText(model.getName());
        holder.tv_location.setText(model.getLocation());
    }

    @NonNull
    @Override
    public TournamentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tournament_item, parent, false);
        //v.setOnClickListener((View.OnClickListener) this);
        return new TournamentHolder(v);
    }

    public void deleteItem (int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    // public void setOnClickListener(View.OnClickListener listener){ this.listener = listener; }
}
