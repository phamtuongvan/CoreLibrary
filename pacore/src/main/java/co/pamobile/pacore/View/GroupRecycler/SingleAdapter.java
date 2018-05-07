package co.pamobile.pacore.View.GroupRecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import co.pamobile.pacore.Utilities.ArrayConvert;
import co.pamobile.pacore.View.RecycleViewAdapterPattern;
import co.pamobile.pacore.View.RecyclerItemClickListener;
import co.pamobile.pacore.R;

/**
 * Created by Dev02 on 4/17/2017.
 */


public class SingleAdapter extends RecycleViewAdapterPattern {
    public RecyclerItemClickListener.OnItemClickListener onItemClickListener;
    public SingleAdapter(Context context, ArrayList<Object> dataSource) {
        super(context,dataSource);
    }

    public void setItemClickListener(RecyclerItemClickListener.OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_default, null);
        return new SingleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SingleViewHolder singleViewHolder = (SingleViewHolder)holder;
        if (onItemClickListener!=null){
            singleViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v,position);
                }
            });
        }
        final ArrayList<SingleItem> lessonItems = ArrayConvert.toArrayList(getDataSource());
    }

}