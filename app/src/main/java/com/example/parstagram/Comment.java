package com.example.parstagram;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Comment")
public class Comment extends ParseObject {
    public static final String KEY_USER = "user";
    public static final String KEY_POST = "post";
    public static final String KEY_COMMENT = "comment";

    public Post getPost() {
        return (Post) getParseObject(KEY_POST);
    }

    public void setPost(Post post) {
        put(KEY_POST, post);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public String getComment() {
        return getString(KEY_COMMENT);
    }

    public void setComment(String comment) {
        put(KEY_COMMENT, comment);
    }
}
