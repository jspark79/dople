package com.dople.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dople.R;
import com.dople.data.DopleChatData;

public class SubLayout extends LinearLayout {
    public SubLayout(Context context, AttributeSet attrs, DopleChatData sampleItem) {
        super(context, attrs);
        init(context, sampleItem);
    }

    public SubLayout(Context context, DopleChatData sampleItem) {
        super(context);
        init(context, sampleItem);
    }


    private void init(Context context, DopleChatData sampleItem) {
        Log.v("dople", "init mess : " + sampleItem.getMessage());
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.dople_chat_line_item, this, true);

        ImageView img = (ImageView)findViewById(R.id.chat_image);
        TextView message_1 = (TextView)findViewById(R.id.chat_item_1_message);
        TextView time_1 = (TextView)findViewById(R.id.chat_item_1_time);
        TextView message_2 = (TextView)findViewById(R.id.chat_item_2_message);
        TextView time_2 = (TextView)findViewById(R.id.chat_item_2_time);

        if (sampleItem.getUser() == 1) {
            message_1.setText(sampleItem.getMessage());
            time_1.setText(sampleItem.getTime());
            message_2.setVisibility(INVISIBLE);
            time_2.setVisibility(INVISIBLE);
        } else {
            message_2.setText(sampleItem.getMessage());
            time_2.setText(sampleItem.getTime());
            img.setVisibility(INVISIBLE);
            message_1.setVisibility(INVISIBLE);
            time_1.setVisibility(INVISIBLE);
        }
    }
}
