package co.pamobile.pacore.View;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by KhoaVin on 1/24/2017.
 */

public class ViewHolderPattern extends RecyclerView.ViewHolder{

    public ViewHolderPattern(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
