package com.shura.rmct2.look;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shura.rmct2.R;

/**
 * Created by lemon on 2018/2/6.
 */

public class TypeThreeViewHolder extends TypeAbstractViewHolder{

    public ImageView avator;
    public TextView name;
    public TextView content ;
    public ImageView contentImage;

    public TypeThreeViewHolder(View itemView) {
        super(itemView);
        avator = itemView.findViewById(R.id.avator);
        name = itemView.findViewById(R.id.name);
        content = itemView.findViewById(R.id.content);
        contentImage = itemView.findViewById(R.id.contentImage);
    }

    @Override
    public void bindHolder(DataModel model) {
        avator.setBackgroundResource(model.avatarColor);
        name.setText(model.name);
        content.setText(model.content);
        contentImage.setBackgroundResource(model.contentColor);
    }

}
