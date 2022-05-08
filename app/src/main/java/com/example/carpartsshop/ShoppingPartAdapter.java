package com.example.carpartsshop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

public class ShoppingPartAdapter extends RecyclerView.Adapter<ShoppingPartAdapter.ViewHolder> implements Filterable {

    private ArrayList<ShoppingPart> mShoppingPartsData = new ArrayList<>();
    private ArrayList<ShoppingPart> mShoppingPartsData_All = new ArrayList<>();;
    private Context mContext;
    private int lastPos = -1;

    ShoppingPartAdapter(Context context, ArrayList<ShoppingPart> partsData){
        this.mShoppingPartsData = partsData;
        this.mShoppingPartsData_All = partsData;
        this.mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_parts,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingPartAdapter.ViewHolder holder, int position) {
        ShoppingPart item = mShoppingPartsData.get(position);

        holder.bindTo(item);
        if(holder.getBindingAdapterPosition()> lastPos){
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPos = holder.getBindingAdapterPosition();

        }
    }

    @Override
    public int getItemCount() {
        return mShoppingPartsData.size();
    }
    public Filter shoppingPartFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ShoppingPart> filteredList = new ArrayList<>();
            FilterResults  res = new FilterResults();
            if(charSequence == null || charSequence.length() == 0){
                res.count = mShoppingPartsData_All.size();
                res.values = mShoppingPartsData_All;
            }else{
              String filterPattern = charSequence.toString().toLowerCase().trim();
              for (ShoppingPart item : mShoppingPartsData_All){
                  if(item.getInfo().toLowerCase().contains(filterPattern)){
                      filteredList.add(item);
                  }
              }
                res.count = filteredList.size();
                res.values = filteredList;

            }

            return res;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mShoppingPartsData = (ArrayList) filterResults.values;
                notifyDataSetChanged();

        }
    };
    @Override
    public Filter getFilter() {

        return shoppingPartFilter;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mPartName;
        private TextView mPartInfo;
        private TextView mPrice;
        private ImageView mPartPicture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mPartName = itemView.findViewById(R.id.partName);
            mPartInfo= itemView.findViewById(R.id.partInfo);
            mPrice= itemView.findViewById(R.id.price);
            mPartPicture= itemView.findViewById(R.id.partPicture);

            itemView.findViewById(R.id.addToCart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((PartListActivity)mContext).updateAlertIcon();

                }
            });
        }

        public void bindTo(ShoppingPart item) {
            //feltoltes ertekekkel
            mPartName.setText(item.getName());
            mPartInfo.setText(item.getInfo());
            mPrice.setText(item.getPrice());
            //glideal kepfeltoltes
            Glide.with(mContext).load(item.getImageRes()).into(mPartPicture);


        }
    }




}
