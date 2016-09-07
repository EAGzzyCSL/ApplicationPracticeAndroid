package com.bit.schoolcomment.fragment.school;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.event.school.SchoolListEvent;
import com.bit.schoolcomment.event.school.SchoolSelectEvent;
import com.bit.schoolcomment.fragment.BaseListFragment;
import com.bit.schoolcomment.model.SchoolModel;
import com.bit.schoolcomment.util.PreferenceUtil;
import com.bit.schoolcomment.util.PullUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SchoolListFragment extends BaseListFragment<SchoolModel> {

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new SchoolListAdapter();
    }

    @Override
    protected void pullNewData() {
        PullUtil.getInstance().getSchool();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleSchoolList(SchoolListEvent event) {
        updateUI(event.schoolListModel.data);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleSchoolSelect(SchoolSelectEvent event) {
        PreferenceUtil.putInt("schoolId", event.schoolModel.ID);
        PreferenceUtil.putString("schoolName", event.schoolModel.name);
    }

    private class SchoolListAdapter extends BaseListAdapter<SchoolViewHolder>
            implements View.OnClickListener {

        @Override
        public SchoolViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_school, parent, false);
            SchoolViewHolder holder = new SchoolViewHolder(view);
            holder.itemView.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(SchoolViewHolder holder, int position) {
            holder.itemView.setLabelFor(position);
            SchoolModel model = getModel(position);
            holder.nameTv.setText(model.name);
        }

        @Override
        public void onClick(View v) {
            EventBus.getDefault().post(new SchoolSelectEvent(getModel(v.getLabelFor())));
        }
    }

    private static class SchoolViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTv;

        public SchoolViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.item_school_name);
        }
    }
}
