package com.example.parstagram;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false); // inflate view
        return new ViewHolder(view); // bind view with view holder
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        try {
            holder.bind(post);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements com.example.parstagram.ViewHolder {

        private TextView tvUsername;
        private TextView tvCaption;
        private ImageView ivPostPic;
        private ImageView ivPostProfile;
        private ImageView ivHeart;
        private TextView tvLikes;
        JSONArray likers;
        boolean isLiked;
        int userIndex;
        String objectId;

        public ViewHolder(@NonNull View itemView) { // identify components of view
            super(itemView);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivPostPic = itemView.findViewById(R.id.ivPostPic);
            ivPostProfile = itemView.findViewById(R.id.ivPostProfile);
            ivHeart = itemView.findViewById(R.id.ivHeart);
            tvLikes = itemView.findViewById(R.id.tvLikes);
        }

        public void bind(final Post post) throws JSONException { // bind each component to the view holder
            tvCaption.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            ivPostPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) { // make sure position is valid
                        // extract movie from list
                        Post post = posts.get(position);

                        // create new intent
                        Intent intent = new Intent(context, PostDetailsActivity.class);

                        // pass movie as an extra serialized via Parcels.wrap()
                        intent.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));

                        // show activity
                        context.startActivity(intent);
                    }
                }
            });

            likers = new JSONArray();
            JSONArray likes = post.getLikers();
            Log.i("PostsAdapter", likes.toString());
            tvLikes.setText(String.valueOf(likes.length()));
            post.put(Post.KEY_LIKERS, likers);
            isLiked = false;
            userIndex = -1;
            objectId = ParseUser.getCurrentUser().getObjectId();
            for (int i = 0; i < likes.length(); i++) {
                JSONObject json = likes.getJSONObject(i);
                String like = json.getString("objectId");
                if (like.equals(objectId)) {
                    isLiked = true;
                    userIndex = i;
                }
            }

            if (isLiked) {
                ivHeart.setImageResource(R.drawable.ic_vector_heart_pink);
            } else {
                ivHeart.setImageResource(R.drawable.ic_vector_heart);
            }



            Glide.with(context).load(post.getImage().getUrl()).into(ivPostPic);

            ParseFile image = post.getUser().getParseFile("profilePic");
            if (image != null) {
                Glide.with(context).load(image.getUrl()).circleCrop().into(ivPostProfile);
            }

            ivHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isLiked) {
                        likers.put(ParseUser.getCurrentUser());
                    } else {
                        likers.remove(userIndex);
                    }
                    post.put(Post.KEY_LIKERS, likers);
                    post.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                Log.e("PostsAdapter", "error saving likes");
                            }
                        }
                    });
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }
}

