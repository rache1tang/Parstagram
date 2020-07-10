package com.example.parstagram;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

public class ComposeCommentActivity extends AppCompatActivity {

    ImageView ivCommentExit;
    EditText etCommentText;
    Button btnCommentSubmit;
    String commentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_comment);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_USE_LOGO);
        getSupportActionBar().setIcon(R.drawable.nav_logo_whiteout);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ivCommentExit = findViewById(R.id.ivCommentExit);
        etCommentText = findViewById(R.id.etCommentText);
        btnCommentSubmit = findViewById(R.id.btnCommentSubmit);

        Intent intent = getIntent();
        final Post post = Parcels.unwrap(intent.getParcelableExtra(PostsAdapter.KEY_POST));
        String username = post.getUser().getUsername();
        etCommentText.setText("@" + username);

        ivCommentExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etCommentText.setText("");
                finish();
            }
        });

        btnCommentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentText = etCommentText.getText().toString();

                if (commentText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Comment cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Comment comment = new Comment();
                comment.setPost(post);
                comment.setUser(ParseUser.getCurrentUser());
                comment.setComment(commentText);

                Intent intent = new Intent();
                intent.putExtra(PostsAdapter.KEY_NEW_COMMENT, Parcels.wrap(comment));

                setResult(RESULT_OK, intent);

                finish();
            }
        });

    }
}