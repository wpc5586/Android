package com.aaron.aaronlibrary.easeui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aaron.aaronlibrary.easeui.utils.RedPacketUtil;
import com.aaron.aaronlibrary.easeui.widget.chatrow.EaseChatRow;
import com.aaron.interview.R;
import com.easemob.redpacketsdk.constant.RPConstant;
import com.hyphenate.chat.EMMessage;

public class ChatRowRandomPacket extends EaseChatRow {

    private TextView mTvGreeting;

    public ChatRowRandomPacket(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        keepFontSize();
        if (RedPacketUtil.isRandomRedPacket(message)) {
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    R.layout.em_row_received_random_packet : R.layout.em_row_sent_random_packet, this);
        }
    }

    @Override
    protected void onFindViewById() {
        mTvGreeting = (TextView) findViewById(R.id.tv_money_greeting);
    }

    @Override
    protected void onSetUpView() {
        String greetings = message.getStringAttribute(RPConstant.EXTRA_RED_PACKET_GREETING, "");
        mTvGreeting.setText(greetings);
    }

    @Override
    protected void onUpdateView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onBubbleClick() {
    }

    public void keepFontSize(){
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }
}
