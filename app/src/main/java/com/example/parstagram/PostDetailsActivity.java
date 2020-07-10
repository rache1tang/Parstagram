package com.example.parstagram;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class PostDetailsActivity extends AppCompatActivity {

    TextView tvDetailsUsername;
    TextView tvDetailsTime;
    TextView tvDetailsCaption;
    ImageView ivDetailsImage;
    ImageView ivDetailsExit;
    ImageView ivDetailsProfile;

    RecyclerView rvComments;
    List<Comment> allComments;
    CommentsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_USE_LOGO);
        getSupportActionBar().setIcon(R.drawable.nav_logo_whiteout);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        rvComments = findViewById(R.id.rvComments);
        allComments = new ArrayList<>();
        adapter = new CommentsAdapter(getApplicationContext(), allComments);

        tvDetailsUsername = findViewById(R.id.tvDetailUsername);
        tvDetailsTime = findViewById(R.id.tvDetailsTime);
        tvDetailsCaption = findViewById(R.id.tvDetailsCaption);
        ivDetailsImage = findViewById(R.id.ivDetailsImage);
        ivDetailsExit = findViewById(R.id.ivDetailsExit);
        ivDetailsProfile = findViewById(R.id.ivDetailsProfile);

        Post post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
        tvDetailsTime.setText(post.getTime());
        tvDetailsUsername.setText(post.getUser().getUsername());
        tvDetailsCaption.setText(post.getDescription());

        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(getApplicationContext()).load(image.getUrl()).into(ivDetailsImage);
        }

        ParseFile profile = post.getUser().getParseFile("profilePic");
        if (profile != null) {
            Glide.with(getApplicationContext()).load(profile.getUrl()).circleCrop().into(ivDetailsProfile);
        }

        ivDetailsExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rvComments.setAdapter(adapter);
        rvComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Log.i("PostDetailsActivity", "hihihihiihihhh");
        loadComments(post);
    }

    private void loadComments(Post post) {
        Log.i("PostDetailsActivity", "loading comments");
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        query.include(Comment.KEY_POST);
        query.whereEqualTo(Comment.KEY_POST, post);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_AT);

        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> objects, ParseException e) {
                if (e != null) {
                    Log.e("PostDetailsActivity", "error loading comments", e);
                    return;
                }

                adapter.clear();
                allComments.addAll(objects);
                adapter.notifyDataSetChanged();

            }
        });
    }
}