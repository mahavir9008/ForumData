package demo.com.pocapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import demo.com.pocapplication.R;
import demo.com.pocapplication.model.ForumData;
import demo.com.pocapplication.pojo.Comment;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private final OnItemClickListener listener;
    private List<?> data = new ArrayList<>();
    private final boolean isComment;

    public RecycleAdapter(Boolean isCommentData, Context context, OnItemClickListener listener) {
        this.listener = listener;
        this.isComment = isCommentData;
    }

    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycleAdapter.ViewHolder holder, int position) {

        // update the data according to fragment
        if (isComment) {
            ForumData forumData = (ForumData) data.get(position);
            holder.click(forumData, listener);
            holder.tvForumTitle.setText(forumData.getTitle());
            holder.tvDForumDesc.setText(forumData.getBody());
            holder.tvDForumUser.setText(forumData.getAuthorName());
        } else {
            Comment commentData = (Comment) data.get(position);
            holder.click(commentData, listener);
            holder.tvForumTitle.setText(commentData.getName());
            holder.tvDForumDesc.setText(commentData.getEmail());
            holder.tvDForumUser.setText(commentData.getBody());
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public interface OnItemClickListener {
        void onClick(Object Item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.forum_title)
        TextView tvForumTitle;
        @BindView(R.id.forum_body)
        TextView tvDForumDesc;
        @BindView(R.id.forum_user)
        TextView tvDForumUser;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void click(Object data, OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onClick(data));
        }

    }

    public void addData(List<?> commentData) {
        this.data = commentData;
        notifyDataSetChanged();
    }

    public Integer getUserId(){
        return ((Comment)this.data.get(0)).getPostId();

    }

}