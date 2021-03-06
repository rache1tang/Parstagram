package com.example.parstagram;

import android.util.Log;

import com.example.parstagram.fragments.PostsFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileScrollFragment extends PostsFragment {
    @Override
    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser()); // filter posts by current user
        query.setLimit(20); // limit the number of posts to 20
        query.addDescendingOrder(Post.KEY_CREATED_AT); // order by date post created
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + " username: " + post.getUser().getUsername());
                }
                adapter.clear();
                allPosts.addAll(posts);
                swipeContainer.setRefreshing(false);
            }
        });
    }
}
