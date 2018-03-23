package com.shura.rmct2.look;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shura.rmct2.R;

/**
 * Created by lemon on 2018/2/6.
 */

public class TypeOneViewHolder extends TypeAbstractViewHolder{

    public ImageView avator;
    public TextView name;

    public TypeOneViewHolder(View itemView) {
        super(itemView);
        avator = itemView.findViewById(R.id.avator);
        name = itemView.findViewById(R.id.name);
    }

    @Override
    public void bindHolder(DataModel model) {
        avator.setBackgroundResource(model.avatarColor);
        name.setText(model.name);
    }

}
