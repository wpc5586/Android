package com.aaron.interview.activity;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aaron.aaronlibrary.base.domain.BaseViewHolder;
import com.aaron.aaronlibrary.listener.OnRecyclerItemClickListener;
import com.aaron.aaronlibrary.widget.stickyheader.AnimalsAdapter;
import com.aaron.aaronlibrary.widget.stickyheader.DividerDecoration;
import com.aaron.aaronlibrary.widget.stickyheader.RecyclerItemClickListener;
import com.aaron.interview.R;
import com.aaron.interview.base.InterViewActivity;
import com.aaron.interview.bean.ResumeTypeVo;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersTouchListener;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * 增加简历类型选择页面
 * Created by Aaron on 2017/11/15.
 */

public class ResumeTypeActivity extends InterViewActivity {

    private String[] menuTitles = new String[]{"文字", "图片"};

    private String[][] typeIndexs = new String[][]{new String[]{"text1", "text2"},
            new String[]{"image1"}};

    private String[][] typeNames = new String[][]{new String[]{"标准文字", "气泡文字"},
            new String[]{"图片"}};

    private List<ResumeTypeVo> types = new ArrayList<>();

    private RecyclerView recyclerView1, recyclerView2;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_resume_type;
    }

    @Override
    protected void findView() {
        super.findView();
        recyclerView1 = (RecyclerView) findViewById(R.id.recycler1);
        recyclerView2 = (RecyclerView) findViewById(R.id.recycler2);
    }

    @Override
    protected void init() {
        super.init();
        initTypeMenu();
        setRecyclerView();
    }

    /**
     * 初始化类型菜单
     */
    private void initTypeMenu() {
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < menuTitles.length; i++) {
                types.add(new ResumeTypeVo(menuTitles[i], typeIndexs[i], typeNames[i]));
            }
        }
    }

    /**
     * 设置菜单和类型详情
     */
    private void setRecyclerView() {
        recyclerView1.setAdapter(new ResumeMenuAdapter());
        recyclerView1.setLayoutManager(new LinearLayoutManager(mContext));

        // Set adapter populated with example dummy data
        final AnimalsHeadersAdapter adapter = new AnimalsHeadersAdapter();
        adapter.add("Animals below!");
        adapter.addAll(new String[]{"1", "2", "3"});
        recyclerView2.setAdapter(adapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(mContext));
        // Add the sticky headers decoration
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        recyclerView2.addItemDecoration(headersDecor);

        // Add decoration for dividers between list items
        recyclerView2.addItemDecoration(new DividerDecoration(this));

        // Add touch listeners
        StickyRecyclerHeadersTouchListener touchListener =
                new StickyRecyclerHeadersTouchListener(recyclerView2, headersDecor);
        touchListener.setOnHeaderClickListener(
                new StickyRecyclerHeadersTouchListener.OnHeaderClickListener() {
                    @Override
                    public void onHeaderClick(View header, int position, long headerId) {
                        Toast.makeText(mContext, "Header position: " + position + ", id: " + headerId,
                                Toast.LENGTH_SHORT).show();
                    }
                });
        recyclerView2.addOnItemTouchListener(touchListener);
        recyclerView2.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.remove(adapter.getItem(position));
            }
        }));
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });
    }

    private class ResumeMenuAdapter extends RecyclerView.Adapter<ResumeMenuHolder> implements View.OnClickListener {


        private OnRecyclerItemClickListener onItemClickListener;

        public void setOnItemClickListener(OnRecyclerItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public ResumeMenuAdapter() {
        }

        @Override
        public ResumeMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ResumeMenuHolder(LayoutInflater.from(mContext).
                    inflate(R.layout.item_resume_type_menu, parent, false));
        }

        @Override
        public void onBindViewHolder(ResumeMenuHolder holder, int position) {
            ResumeTypeVo data = getItem(position);
            holder.data = data;
            holder.parent.setTag(holder);
            holder.parent.setOnClickListener(this);
            holder.tvTitle.setText(data.getMenuTitle());
        }

        @Override
        public int getItemCount() {
            return types.size();
        }

        public ResumeTypeVo getItem(int position) {
            return types.get(position);
        }

        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.parent:
                    ResumeMenuHolder holder = (ResumeMenuHolder) v.getTag();
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(v, holder);
                    break;
            }
        }
    }

    static class ResumeMenuHolder extends BaseViewHolder {
        RelativeLayout parent;
        TextView tvTitle;

        public ResumeMenuHolder(View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            tvTitle = itemView.findViewById(R.id.title);
        }
    }

    private class ResumeTypeListAdapter extends RecyclerView.Adapter<ResumeTypeListHolder> implements View.OnClickListener {

        private OnRecyclerItemClickListener onItemClickListener;

        public void setOnItemClickListener(OnRecyclerItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public ResumeTypeListAdapter() {

        }

        @Override
        public ResumeTypeListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ResumeTypeListHolder(LayoutInflater.from(mContext).
                    inflate(R.layout.item_resume_type_menu, parent, false));
        }

        @Override
        public void onBindViewHolder(ResumeTypeListHolder holder, int position) {
            String data = getItem(position);
            holder.data = data;
            holder.parent.setTag(holder);
            holder.parent.setOnClickListener(this);
        }

        @Override
        public int getItemCount() {
            return menuTitles.length;
        }

        public String getItem(int position) {
            return menuTitles[position];
        }

        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.parent:
                    ResumeTypeListHolder holder = (ResumeTypeListHolder) v.getTag();
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(v, holder);
                    break;
            }
        }
    }

    static class ResumeTypeListHolder extends BaseViewHolder {
        RelativeLayout parent;
        Button btnRelease;
        TextView tvName;
        TextView tvTime;
        TextView tvPerson;
        TextView tvState;
        ImageView addView;

        public ResumeTypeListHolder(View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            addView = itemView.findViewById(R.id.add);
        }
    }

    private class AnimalsHeadersAdapter extends AnimalsAdapter<RecyclerView.ViewHolder>
            implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_resume_type_menu, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//            TextView textView = (TextView) holder.itemView;
//            textView.setText(getItem(position));
        }

        @Override
        public long getHeaderId(int position) {
            if (position == 0) {
                return -1;
            } else {
                return getItem(position).charAt(0);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_resume_type_menu, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
//            TextView textView = (TextView) holder.itemView;
//            textView.setText(String.valueOf(getItem(position).charAt(0)));
//            holder.itemView.setBackgroundColor(getRandomColor());
        }

        private int getRandomColor() {
            SecureRandom rgen = new SecureRandom();
            return Color.HSVToColor(150, new float[]{
                    rgen.nextInt(359), 1, 1
            });
        }
    }
}