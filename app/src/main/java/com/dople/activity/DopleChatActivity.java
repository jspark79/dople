package com.dople.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.dople.R;
import com.dople.adapter.SubLayout;
import com.dople.data.DopleChatData;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DopleChatActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mChatMessage;

    private ScrollView mScroll;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dople_chat);

        initView();
        initChatData();
    }

    private void initView() {
        ((ImageView) findViewById(R.id.dople_chat_send)).setOnClickListener(this);

        ImageButton backBtn = (ImageButton) findViewById(R.id.dople_chat_back_btn);
        backBtn.setColorFilter(Color.parseColor("#ffffff"));
        backBtn.setOnClickListener(this);

        mChatMessage = (EditText) findViewById(R.id.dople_chat_inputtext);

        mScroll = (ScrollView) findViewById(R.id.dople_chat_scroll);
        layout = (LinearLayout)findViewById(R.id.dople_chat_message);


        mScroll.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                mScroll.post(new Runnable() {
                    @Override
                    public void run() {
                        mScroll.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dople_chat_back_btn:
                onBackPressed();
                break;
            case R.id.dople_chat_send:
                SimpleDateFormat ft = new SimpleDateFormat("MM-dd hh:mm");
                Calendar tempCal = Calendar.getInstance();

                DopleChatData chatData = new DopleChatData();
                String dateStr = ft.format(tempCal.getTime());
                chatData.setUser(2);
                chatData.setMessage(mChatMessage.getText().toString());
                chatData.setTime(dateStr);

                mChatMessage.setText("");
                SubLayout subLayout = new SubLayout(this, chatData);
                layout.addView(subLayout);
                break;
        }
    }


    private void initChatData() {
        SimpleDateFormat ft = new SimpleDateFormat("MM-dd HH:mm");
        Calendar tempCal = Calendar.getInstance();
        tempCal.set(2022, 7, 17, 15, 44);

        DopleChatData chatData = new DopleChatData();
        tempCal.add(Calendar.MINUTE, -40);
        String dateStr = ft.format(tempCal.getTime());
        chatData.setUser(2);
        chatData.setMessage("안녕하세요 한 가지 문의드리려고 하는데요");
        chatData.setTime(dateStr);
        SubLayout subLayout = new SubLayout(this, chatData);
        layout.addView(subLayout);

        chatData = new DopleChatData();
        tempCal.add(Calendar.MINUTE, 2);
        dateStr = ft.format(tempCal.getTime());
        chatData.setUser(1);
        chatData.setMessage("네 말씀하세요~");
        chatData.setTime(dateStr);
        subLayout = new SubLayout(this, chatData);
        layout.addView(subLayout);

        chatData = new DopleChatData();
        tempCal.add(Calendar.MINUTE, 2);
        dateStr = ft.format(tempCal.getTime());
        chatData.setUser(2);
        chatData.setMessage("수영복 이외에 준비물이 따로 있나요?");
        chatData.setTime(dateStr);
        subLayout = new SubLayout(this, chatData);
        layout.addView(subLayout);

        chatData = new DopleChatData();
        tempCal.add(Calendar.MINUTE, 1);
        dateStr = ft.format(tempCal.getTime());
        chatData.setUser(2);
        chatData.setMessage("킥판은 대여 가능한가요??");
        chatData.setTime(dateStr);
        subLayout = new SubLayout(this, chatData);
        layout.addView(subLayout);

        chatData = new DopleChatData();
        tempCal.add(Calendar.MINUTE, 3);
        dateStr = ft.format(tempCal.getTime());
        chatData.setUser(1);
        chatData.setMessage("수영복, 수모, 수경, 기본적인 세면도구와 수건은 챙겨오셔야 합니다.");
        chatData.setTime(dateStr);
        subLayout = new SubLayout(this, chatData);
        layout.addView(subLayout);

        chatData = new DopleChatData();
        tempCal.add(Calendar.MINUTE, 2);
        dateStr = ft.format(tempCal.getTime());
        chatData.setUser(1);
        chatData.setMessage("킥판은 수영장에서 무료로 대여해주는 걸로 알고있어요~");
        chatData.setTime(dateStr);
        subLayout = new SubLayout(this, chatData);
        layout.addView(subLayout);

        chatData = new DopleChatData();
        tempCal.add(Calendar.MINUTE, 5);
        dateStr = ft.format(tempCal.getTime());
        chatData.setUser(2);
        chatData.setMessage("답변 감사합니다");
        chatData.setTime(dateStr);
        subLayout = new SubLayout(this, chatData);
        layout.addView(subLayout);

        chatData = new DopleChatData();
        tempCal.add(Calendar.MINUTE, 1);
        dateStr = ft.format(tempCal.getTime());
        chatData.setUser(2);
        chatData.setMessage("혹시 지금 참여 인원이 몇 명정도 있나요?");
        chatData.setTime(dateStr);
        subLayout = new SubLayout(this, chatData);
        layout.addView(subLayout);

        chatData = new DopleChatData();
        tempCal.add(Calendar.MINUTE, 2);
        dateStr = ft.format(tempCal.getTime());
        chatData.setUser(1);
        chatData.setMessage("평균 8명 내외로 생각하시면 됩니다.");
        chatData.setTime(dateStr);
        subLayout = new SubLayout(this, chatData);
        layout.addView(subLayout);

        chatData = new DopleChatData();
        tempCal.add(Calendar.MINUTE, 2);
        dateStr = ft.format(tempCal.getTime());
        chatData.setUser(1);
        chatData.setMessage("연령대의 경우," +
                "주중 새벽시간은 주로 직장인 분들이 많고 " +
                "아침 시간 이후에는 주부들이 많은 편입니다.");
        chatData.setTime(dateStr);
        subLayout = new SubLayout(this, chatData);
        layout.addView(subLayout);

        chatData = new DopleChatData();
        tempCal.add(Calendar.MINUTE, 3);
        dateStr = ft.format(tempCal.getTime());
        chatData.setUser(2);
        chatData.setMessage("수영을 시작한지 한 달 밖에 되지 않았는데 괜찮을까요?");
        chatData.setTime(dateStr);
        subLayout = new SubLayout(this, chatData);
        layout.addView(subLayout);

        chatData = new DopleChatData();
        tempCal.add(Calendar.MINUTE, 2);
        dateStr = ft.format(tempCal.getTime());
        chatData.setUser(1);
        chatData.setMessage("네 초보 분들이 많아서 그런 부분은 걱정하시지 않으셔도 됩니다^^");
        chatData.setTime(dateStr);
        subLayout = new SubLayout(this, chatData);
        layout.addView(subLayout);

        chatData = new DopleChatData();
        tempCal.add(Calendar.MINUTE, 0);
        dateStr = ft.format(tempCal.getTime());
        chatData.setUser(1);
        chatData.setMessage("혹시 원하시는 시간대가 있으신가요?");
        chatData.setTime(dateStr);
        subLayout = new SubLayout(this, chatData);
        layout.addView(subLayout);

        chatData = new DopleChatData();
        tempCal.add(Calendar.MINUTE, 1);
        dateStr = ft.format(tempCal.getTime());
        chatData.setUser(2);
        chatData.setMessage("아직 구체적으로 생각해보진 않았는데, 평일 아침 6시에서 7시가 좋을 것 같습니다.");
        chatData.setTime(dateStr);
        subLayout = new SubLayout(this, chatData);
        layout.addView(subLayout);

        chatData = new DopleChatData();
        tempCal.add(Calendar.MINUTE, 1);
        dateStr = ft.format(tempCal.getTime());
        chatData.setUser(1);
        chatData.setMessage("아 평일 오전 수업이면 실력이 비슷한 분들이 많으셔서 따라가기 어렵지 않을 것 같네요!");
        chatData.setTime(dateStr);
        subLayout = new SubLayout(this, chatData);
        layout.addView(subLayout);

        chatData = new DopleChatData();
        tempCal.add(Calendar.MINUTE, 0);
        dateStr = ft.format(tempCal.getTime());
        chatData.setUser(2);
        chatData.setMessage("네 감사합니다~ 일정 보고 신청할게요!");
        chatData.setTime(dateStr);
        subLayout = new SubLayout(this, chatData);
        layout.addView(subLayout);

        chatData = new DopleChatData();
        tempCal.add(Calendar.MINUTE, 1);
        dateStr = ft.format(tempCal.getTime());
        chatData.setUser(1);
        chatData.setMessage("넵 감사합니다~^^");
        chatData.setTime(dateStr);
        subLayout = new SubLayout(this, chatData);
        layout.addView(subLayout);
    }

}