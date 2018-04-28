package com.voltiosx.nolti.wavescore.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.voltiosx.nolti.wavescore.R;
import com.voltiosx.nolti.wavescore.models.InstanceItem;
import com.voltiosx.nolti.wavescore.models.ListItem;
import com.voltiosx.nolti.wavescore.models.ResultItem;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // :: 1A :: Declaro el titulo para INSTANCE HEAT hl_instance_heat
	private static class InstanceViewHolder extends RecyclerView.ViewHolder {

		//TextView txt_header;
        TextView hl_instance_heat;

		InstanceViewHolder(View itemView) {
			super(itemView);
            hl_instance_heat = (TextView) itemView.findViewById(R.id.hl_instance_heat);
			//txt_header = (TextView) itemView.findViewById(R.id.txt_header);
		}

	} //OK

    // :: 1B :: Declaro los datos para TABLE RESULT HEAT
	private static class TableRsultViewHolder extends RecyclerView.ViewHolder {
        Context context;
        TableLayout tablelayout;
        TextView tv_name, tv_hometown, tv_ranking,
                tv_wave1, tv_wave2, tv_wave3, tv_wave4, tv_wave5, tv_wave6, tv_wave7, tv_wave8, tv_wave9, tv_wave10, tv_total_score;
		//TextView txt_title;
		TableRsultViewHolder(View v) {
			super(v);
			context = v.getContext();
            tablelayout = (TableLayout) v.findViewById(R.id.result_table);
            tv_name = (TextView) v.findViewById(R.id.tv_name);
            tv_hometown = (TextView) v.findViewById(R.id.tv_hometown);
            tv_ranking = (TextView) v.findViewById(R.id.tv_ranking);
            tv_wave1 = (TextView) v.findViewById(R.id.tv_wave1);
            tv_wave2 = (TextView) v.findViewById(R.id.tv_wave2);
            tv_wave3 = (TextView) v.findViewById(R.id.tv_wave3);
            tv_wave4 = (TextView) v.findViewById(R.id.tv_wave4);
            tv_wave5 = (TextView) v.findViewById(R.id.tv_wave5);
            tv_wave6 = (TextView) v.findViewById(R.id.tv_wave6);
            tv_wave7 = (TextView) v.findViewById(R.id.tv_wave7);
            tv_wave8 = (TextView) v.findViewById(R.id.tv_wave8);
            tv_wave9 = (TextView) v.findViewById(R.id.tv_wave9);
            tv_wave10 = (TextView) v.findViewById(R.id.tv_wave10);
            tv_total_score = (TextView) v.findViewById(R.id.tv_total_score);
			//txt_title = (TextView) v.findViewById(R.id.txt_title);
		}

	} //OK

    // :: 2 :: Listo los tipos de items
	@NonNull
	private List<ListItem> items;

    // :: 3 :: metodo Adaptador publico que recibe los items
	public ResultsAdapter(@NonNull List<ListItem> items) {
		this.items = items;
	}

    // :: 4 :: onCreateViewHolder, selecciono el tipo de View a mostrar
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		switch (viewType) {
			case ListItem.TYPE_TITLE: {
				View itemView = inflater.inflate(R.layout.list_item_result_title, parent, false);
				return new InstanceViewHolder(itemView);
			}
			case ListItem.TYPE_TABLE: {
				View itemView = inflater.inflate(R.layout.list_item_result_table, parent, false);
				return new TableRsultViewHolder(itemView);
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
			case ListItem.TYPE_TITLE: {
				InstanceItem titleitem = (InstanceItem) items.get(position);
				InstanceViewHolder holder = (InstanceViewHolder) viewHolder;
				holder.hl_instance_heat.setText(titleitem.getInstancename());
				break;
			}
			case ListItem.TYPE_TABLE: {
				ResultItem result = (ResultItem) items.get(position);
				TableRsultViewHolder holder = (TableRsultViewHolder) viewHolder;
				// Seteo de todos los datos del objeto resultado
				holder.tv_name.setText(result.getName());
				holder.tv_hometown.setText(result.getHometown());
				holder.tv_ranking.setText(result.getRanking());
				holder.tv_wave1.setText(String.valueOf(result.getWavestaken().get(0)));
                holder.tv_wave2.setText(String.valueOf(result.getWavestaken().get(1)));
                holder.tv_wave3.setText(String.valueOf(result.getWavestaken().get(2)));
                holder.tv_wave4.setText(String.valueOf(result.getWavestaken().get(3)));
                holder.tv_wave5.setText(String.valueOf(result.getWavestaken().get(4)));
                holder.tv_wave6.setText(String.valueOf(result.getWavestaken().get(5)));
                holder.tv_wave7.setText(String.valueOf(result.getWavestaken().get(6)));
                holder.tv_wave8.setText(String.valueOf(result.getWavestaken().get(7)));
                holder.tv_wave9.setText(String.valueOf(result.getWavestaken().get(8)));
                holder.tv_wave10.setText(String.valueOf(result.getWavestaken().get(9)));
                holder.tv_total_score.setText("Total "+result.getScore());
				break;
			}
			default:
				throw new IllegalStateException("Tipo de vista no soportada");
		}
	} //OK

	@Override
	public int getItemCount() {
		return items.size();
	}

	@Override
	public int getItemViewType(int position) {
		return items.get(position).getType();
	}

}