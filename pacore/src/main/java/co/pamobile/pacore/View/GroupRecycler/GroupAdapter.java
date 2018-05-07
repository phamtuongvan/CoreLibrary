package co.pamobile.pacore.View.GroupRecycler;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import co.pamobile.pacore.R;
import co.pamobile.pacore.Utilities.ArrayConvert;
import co.pamobile.pacore.View.RecycleViewAdapterPattern;
import co.pamobile.pacore.View.RecyclerItemClickListener;

/**
 * Created by Dev02 on 4/17/2017.
 */

public class GroupAdapter extends RecycleViewAdapterPattern {



    public GroupAdapter(Context mContext, ArrayList<Object> dataSource) {
        super(mContext, dataSource);
    }
    public GroupOnItemClickListener groupOnItemClickListener;
    public void setGroupOnItemClickListener(GroupOnItemClickListener groupOnItemClickListener) {
        this.groupOnItemClickListener = groupOnItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_items_default, null);
        return new GroupViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        GroupViewHolder mViewHolder = (GroupViewHolder) holder;
        final ArrayList<GroupItem> arrayList = ArrayConvert.toArrayList(getDataSource());
        ArrayList singleSectionItems = arrayList.get(position).getListSingleItem();

        SingleAdapter singleAdapter = new SingleAdapter(getContext(), singleSectionItems);
        singleAdapter.setItemClickListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int column) {
                if(groupOnItemClickListener !=null)
                    groupOnItemClickListener.OnGroupItemClick(position,column);
            }
        });
    }
}
