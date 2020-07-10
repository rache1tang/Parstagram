package com.example.parstagram;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.json.JSONException;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    Context context;
    List<Comment> comments;

    public CommentsAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false); // inflate view
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        try {
            holder.bind(comment);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void clear() {
        comments.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivCommentProfile;
        TextView tvCommentComment;
        TextView tvCommentUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCommentProfile = itemView.findViewById(R.id.ivCommentProfile);
            tvCommentComment = itemView.findViewById(R.id.tvCommentComment);
            tvCommentUser = itemView.findViewById(R.id.tvCommentUser);
        }

        public void bind(Comment comment) throws ParseException {
            ParseUser user = comment.getUser();
            String text = comment.getComment();
            String username = user.fetchIfNeeded().getUsername();
            tvCommentComment.setText(text);
            tvCommentUser.setText(username);

            ParseFile image = user.fetchIfNeeded().getParseFile("profilePic");
            if (image != null) {
                Glide.with(context).load(image.getUrl()).circleCrop().into(ivCommentProfile);
            } else {
                ivCommentProfile.setImageResource(R.drawable.ic_baseline_account_circle_24);
            }
        }
    }
}
