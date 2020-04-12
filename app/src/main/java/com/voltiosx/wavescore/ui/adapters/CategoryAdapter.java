package com.voltiosx.wavescore.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.voltiosx.wavescore.R;
import com.voltiosx.wavescore.models.Category;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private ArrayList<Category> categorys;
    private LayoutInflater inflater;

    public CategoryAdapter (ArrayList<Category> categorys){
        this.categorys = categorys;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_category_form_tournament, parent, false);
        return new CategoryViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return categorys.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryHolder, int position) {
        //int positionH = categoryHolder.getAdapterPosition();
        Category category = categorys.get(position);
        categoryHolder.setData(category, position);
        //categoryHolder.setData(category, positionH);
        categoryHolder.setListeners();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvCategory;
        private TextView tvPosition;
        private ImageView imgDelete;
        private int position;
        Category currentCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            tvPosition = (TextView) itemView.findViewById(R.id.tvPosition);
            imgDelete = (ImageView) itemView.findViewById(R.id.img_delete);
        }

        public void setData(Category currentCategory, int position) {
            this.tvCategory.setText(currentCategory.getName());
            this.tvPosition.setText(String.valueOf(currentCategory.getPosition()));
            this.position = position;
            this.currentCategory = currentCategory;
        }

        public void setListeners() {
            imgDelete.setOnClickListener(CategoryViewHolder.this);
        }

        @Override
        public void onClick(View v){
            switch (v.getId()){
                case R.id.img_delete: removeItem(position);
                break;
            }
        }

    }

    public void addItem(int position, Category currentCategory) {
        categorys.add(currentCategory);
        notifyItemInserted(position);
        notifyDataSetChanged();
        //notifyItemRangeChanged(position, getItemCount());
        //notifyItemRangeChanged(position, categorys.size());
    }

    public void removeItem(int position) {
        categorys.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
        //notifyItemRangeChanged(position, getItemCount());
    }

    public ArrayList<Category> getCategorys() {
        return categorys;
    }

}
