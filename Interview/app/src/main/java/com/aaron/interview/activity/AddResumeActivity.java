package com.aaron.interview.activity;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aaron.aaronlibrary.base.domain.BaseViewHolder;
import com.aaron.aaronlibrary.base.utils.PublicMethod;
import com.aaron.aaronlibrary.http.BaseMap;
import com.aaron.aaronlibrary.http.PostCall;
import com.aaron.aaronlibrary.http.ServerUrl;
import com.aaron.aaronlibrary.listener.OnRecyclerItemClickListener;
import com.aaron.aaronlibrary.utils.AppInfo;
import com.aaron.aaronlibrary.utils.MathUtils;
import com.aaron.interview.R;
import com.aaron.interview.base.InterViewActivity;
import com.aaron.interview.bean.WorkBean;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 增加简历页面
 * Created by Aaron on 2017/11/15.
 */

public class AddResumeActivity extends InterViewActivity {

    private PtrClassicFrameLayout ptrFrameLayout;

    private RecyclerView recyclerView;

    private AddResumeAdapter adapter;

    private List<WorkBean.Obj.WorkList> workLists;

    private int count = 0;

    private int page = 1;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_add_resume;
    }

    @Override
    protected void findView() {
        recyclerView = (RecyclerView) findViewById(R.id.listView);
        ptrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptrFrameLayout);
    }

    @Override
    protected void init() {
        super.init();
        setActionbarTitle("新增简历");
        setRecyclerView();
//        getData();
    }

    private void getData() {
        String[] toastContent = {"", ""};
        PostCall.post(mContext, ServerUrl.getWorkResumeList(), new BaseMap(String.valueOf(page)), new PostCall.PostResponse<WorkBean>() {

            @Override
            public void onSuccess(int i, byte[] bytes, WorkBean workBean) {
                if (page == 1) {
                    workLists = workBean.getObj().getResumeList();
                    count = workBean.getObj().getTotalCount();
                    if (workLists == null)
                        workLists = new ArrayList<>();
                    if (isNotMore()) {
                        isCanDismiss = true;
                        dismissProgressDialog();
                        setRecyclerView();
                    } else {
                        page++;
                        getData();
                    }
                } else if (page == 2) {
                    workLists.addAll(workBean.getObj().getResumeList());
                    setRecyclerView();
                    initPull();
                    isCanDismiss = true;
                    dismissProgressDialog();
                } else {
                    if (ptrFrameLayout != null && ptrFrameLayout.isRefreshing()) {
                        if (workLists != null && workBean != null
                                && workBean.getObj() != null && workBean.getObj().getResumeList() != null) {
                            workLists.addAll(workBean.getObj().getResumeList());
                            adapter.notifyDataSetChanged();
                        } else
                            ptrFrameLayout.setMode(PtrFrameLayout.Mode.NONE);
                        ptrFrameLayout.refreshComplete();
                    }
                }
                isNotMore();
            }

            @Override
            public void onFailure(int i, byte[] bytes) {
            }
        }, toastContent, page == 1, WorkBean.class);
    }

    /**
     * 判断是否没有更多了
     *
     * @return true：没有更多  false：还有下一页
     */
    private boolean isNotMore() {
        boolean isNotMore = workLists.size() == count;
        if (isNotMore)
            ptrFrameLayout.setMode(PtrFrameLayout.Mode.NONE);
        return isNotMore;
    }

    /**
     * 设置RecyclerView
     */
    private void setRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        adapter = new AddResumeAdapter(mContext);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder) {
                // holder.data.getUserId()
            }
        });
    }

    /**
     * 设置上拉下拉
     */
    private void initPull() {
        PublicMethod.setPullView(mContext, ptrFrameLayout, PtrFrameLayout.Mode.LOAD_MORE, new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(final PtrFrameLayout frame) {
                page++;
                getData();
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
            }
        });
    }


    private class AddResumeAdapter extends RecyclerView.Adapter<AddResumeHolder> implements View.OnClickListener {

        private final Context context;

        private OnRecyclerItemClickListener onItemClickListener;

        public void setOnItemClickListener(OnRecyclerItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public AddResumeAdapter(Context context) {
            this.context = context;
            if (workLists == null)
                workLists = new ArrayList<>();
        }

        @Override
        public AddResumeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AddResumeHolder(LayoutInflater.from(context).
                    inflate(R.layout.item_add_resume, parent, false));
        }

        @Override
        public void onBindViewHolder(AddResumeHolder holder, int position) {
            int padding = MathUtils.dip2px(mContext, 10);
            holder.parent.setPadding(padding, padding, padding, padding);
            int height = (AppInfo.getScreenWidthOrHeight(mContext, true) - padding * 6) / 2;
            holder.parent.getLayoutParams().height = height;
            if (position == getItemCount() - 1) {
                holder.addView.setVisibility(View.VISIBLE);
                holder.addView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startMyActivity(ResumeTypeActivity.class);
                    }
                });
            } else {
                WorkBean.Obj.WorkList data = getItem(position);
                holder.data = data;
                holder.parent.setTag(holder);
                holder.parent.setOnClickListener(this);
            }
        }

        @Override
        public int getItemCount() {
            return workLists.size() + 1;
        }

        public WorkBean.Obj.WorkList getItem(int position) {
            return workLists.get(position);
        }

        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.parent:
                    AddResumeHolder holder = (AddResumeHolder) v.getTag();
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(v, holder);
                    break;
//                case R.id.release_Btn:
//                    AddResumeHolder holder1 = (AddResumeHolder) v.getTag();
//                    final String releaseId = ((WorkBean.Obj.WorkList) holder1.data).getReleaseId();
//                    //是否应该发布
//                    boolean isRelease = "0".equals(workLists.get(holder1.getAdapterPosition()).getReleaseFlag());
//                    new AlertDialog.Builder(mContext).setMessage(isRelease ? "是否发布？" : "是否取消发布？")
//                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            release((Button) v, releaseId);
//                            dialog.dismiss();
//                        }
//                    }).show();
//                    break;
            }
        }
    }

    static class AddResumeHolder extends BaseViewHolder {
        RelativeLayout parent;
        Button btnRelease;
        TextView tvName;
        TextView tvTime;
        TextView tvPerson;
        TextView tvState;
        ImageView addView;

        public AddResumeHolder(View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            addView = itemView.findViewById(R.id.add);
//            btnRelease = itemView.findViewById(R.id.release_Btn);
//            tvName = itemView.findViewById(R.id.name);
//            tvTime = itemView.findViewById(R.id.time);
//            tvPerson = itemView.findViewById(R.id.person);
//            tvState = itemView.findViewById(R.id.state);
//            ivArrow = itemView.findViewById(R.id.arrow);
        }
    }
}