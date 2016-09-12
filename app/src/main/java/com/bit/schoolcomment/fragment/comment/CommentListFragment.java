package com.bit.schoolcomment.fragment.comment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bit.schoolcomment.R;
import com.bit.schoolcomment.event.CommentListEvent;
import com.bit.schoolcomment.fragment.BaseListFragment;
import com.bit.schoolcomment.model.CommentModel;
import com.bit.schoolcomment.util.DimensionUtil;
import com.facebook.drawee.view.SimpleDraweeView;

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

    private class CommentListAdapter extends BaseListAdapter<CommentViewHolder> {

        private final int WIDTH = DimensionUtil.Dp2Px(80);
        private final int HEIGHT = DimensionUtil.Dp2Px(60);

        @Override
        public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
            return new CommentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CommentViewHolder holder, int position) {
            CommentModel model = getModel(position);
            holder.nameTv.setText(model.content);
            holder.rateRb.setRating(model.rate);
            holder.contentTv.setText(model.content);
            holder.timeTv.setText(model.time);

            holder.imageLyt.removeAllViews();
            for (int i = 0; i < model.images.size(); i++) {
                holder.imageDv[i].setImageURI(model.images.get(i));
                holder.imageLyt.addView(holder.imageDv[i], new ViewGroup.LayoutParams(WIDTH, HEIGHT));
            }

        }
    }

    private class CommentViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTv;
        private RatingBar rateRb;
        private TextView contentTv;
        private TextView timeTv;
        private LinearLayout imageLyt;
        private SimpleDraweeView[] imageDv;

        public CommentViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.item_comment_name);
            rateRb = (RatingBar) itemView.findViewById(R.id.item_comment_rate);
            contentTv = (TextView) itemView.findViewById(R.id.item_comment_content);
            timeTv = (TextView) itemView.findViewById(R.id.item_comment_time);
            imageLyt = (LinearLayout) itemView.findViewById(R.id.item_comment_image);

            imageDv = new SimpleDraweeView[3];
            for (int i = 0; i < 3; i++) {
                imageDv[i] = new SimpleDraweeView(getContext());
                imageDv[i].getHierarchy().setPlaceholderImage(R.drawable.ic_loading);
            }
        }
    }
}
