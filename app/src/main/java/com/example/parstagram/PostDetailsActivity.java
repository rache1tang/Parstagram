package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

public class PostDetailsActivity extends AppCompatActivity {

    TextView tvDetailsUsername;
    TextView tvDetailsTime;
    TextView tvDetailsCaption;
    ImageView ivDetailsImage;
    ImageView ivDetailsExit;
    ImageView ivDetailsProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

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
    }
}