package com.voltiosx.wavescore.ui.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.voltiosx.wavescore.R;
import com.voltiosx.wavescore.models.FixtureItem;
import com.voltiosx.wavescore.models.HeaderFixtureItem;
import com.voltiosx.wavescore.models.RiderFixtureItem;

import java.util.List;

public class FixtureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // :: 1A :: Declaro el HOLDER para el NUMERO del HEAT
	private static class NumberHeatViewHolder extends RecyclerView.ViewHolder {
        TextView hl_number_heat;
		NumberHeatViewHolder(View v) {
			super(v);
            hl_number_heat = v.findViewById(R.id.hl_number_heat);
		}
	} //OK

    // :: 1B :: Declaro el HOLDER para ROW FIXTURE RIDER
	private static class RiderFixtureViewHolder extends RecyclerView.ViewHolder {
        Context context;
        LinearLayout row;
        TextView name;
        TextView numberheat;
		RiderFixtureViewHolder(View v) {
			super(v);
			context = v.getContext();
            row = v.findViewById(R.id.row);
            name = v.findViewById(R.id.name);
            numberheat = v.findViewById(R.id.position);
		}
	} //OK

    // :: 2 :: Listo los tipos de fixtureitems
	@NonNull
	private List<FixtureItem> fixtureitems;

    // :: 3 :: clase public que recibe los fixtureitems
	public FixtureAdapter(@NonNull List<FixtureItem> fixtureitems) {
		this.fixtureitems = fixtureitems;
	}

    // :: 4 :: onCreateViewHolder, selecciono el tipo de Vista a mostrar
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		switch (viewType) {
			case FixtureItem.TYPE_HEADER: {
				View v = inflater.inflate(R.layout.list_item_fixture_title, parent, false);
				return new NumberHeatViewHolder(v);
			}
			case FixtureItem.TYPE_RIDER: {
				View itemView = inflater.inflate(R.layout.view_list_item_rider, parent, false);
				return new RiderFixtureViewHolder(itemView);
			}
			default:
				throw new IllegalStateException("Tipo de vista no soportada");
		}
	} //OK

    // :: 5 :: onBindViewHolder, seteo los datos
	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
		int viewType = getItemViewType(position);
		switch (viewType) {
			case FixtureItem.TYPE_HEADER: {
				HeaderFixtureItem headeritem = (HeaderFixtureItem) fixtureitems.get(position);
				NumberHeatViewHolder holderheaderheat = (NumberHeatViewHolder) viewHolder;
				holderheaderheat.hl_number_heat.setText(headeritem.getTitulo());
				break;
			}
			case FixtureItem.TYPE_RIDER: {
				RiderFixtureItem rideritem = (RiderFixtureItem) fixtureitems.get(position);
				RiderFixtureViewHolder riderholder = (RiderFixtureViewHolder) viewHolder;
				/* SETEO DE COLORES */
				riderholder.row.setBackgroundColor(rideritem.getTshirtcolor());
				riderholder.name.setTextColor(rideritem.getColortextcontrast());
				riderholder.numberheat.setTextColor(rideritem.getColortextcontrast());
				/* SETEO DE DATOS */
				riderholder.name.setText(rideritem.getName());
				riderholder.numberheat.setText("#"+rideritem.getNumberHeat());
				break;
			}
			default:
				throw new IllegalStateException("Tipo de vista no soportada");
		}
	} //OK

	@Override
	public int getItemCount() {
		return fixtureitems.size();
	}

	@Override
	public int getItemViewType(int position) {
		return fixtureitems.get(position).getType();
	}

}