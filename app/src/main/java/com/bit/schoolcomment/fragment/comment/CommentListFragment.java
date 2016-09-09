package com.bit.schoolcomment.fragment.comment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.event.CommentListEvent;
import com.bit.schoolcomment.fragment.BaseListFragment;
import com.bit.schoolcomment.model.CommentModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class CommentListFragment extends BaseListFragment<CommentModel> {

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new CommentListAdapter();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleCommentList(CommentListEvent event) {
        if (event.targetClass == getClass()) updateUI(event.commentListModel.data);
    }

    private class CommentListAdapter extends BaseListAdapter<CommentViewHolder>
            implements View.OnClickListener {

        @Override
        public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
            CommentViewHolder holder = new CommentViewHolder(view);
            holder.itemView.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(CommentViewHolder holder, int position) {
            CommentModel model = getModel(position);
            holder.nameTv.setText(model.name);
            holder.rateRb.setRating(model.rate);
            holder.contentTv.setText(model.content);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private static class CommentViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTv;
        private RatingBar rateRb;
        private TextView contentTv;

        public CommentViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.item_comment_name);
            rateRb = (RatingBar) itemView.findViewById(R.id.item_comment_rate);
            contentTv = (TextView) itemView.findViewById(R.id.item_comment_content);
        }
    }
}
