package com.example.parstagram;

import android.util.Log;

import androidx.annotation.Nullable;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_USER = "user";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_PROFILE = "profilePic";
    public static final String KEY_LIKERS = "likedUsers";

    public String getDescription() {
        return getString(KEY_DESCRIPTION); //getString() is defined in ParseObject class
    }
    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description); //also defined in ParseObject
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image) {
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public String getTime() {
        Date date = getCreatedAt();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String reportDate = df.format(date);
        return reportDate;
    }

    public JSONArray getLikers() {
        return getJSONArray(KEY_LIKERS);
    }

}

