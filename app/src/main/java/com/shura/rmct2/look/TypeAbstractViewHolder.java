package com.shura.rmct2.look;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lemon on 2018/2/6.
 */

public abstract class TypeAbstractViewHolder extends RecyclerView.ViewHolder {
    public TypeAbstractViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindHolder(DataModel model);
}
