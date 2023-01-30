package com.datechnologies.androidtest.api;

import com.google.gson.annotations.SerializedName;

/**
 * A data model that represents a chat log message fetched from the D & A Technologies Web Server.
 */

public class ChatLogMessageModel
{
    //The SerializedName annotation should always display the exact name of an object in the JSON file.
    @SerializedName("user_id")
    public int userId;

    @SerializedName("avatar_url")
    public String avatarUrl;

    @SerializedName("name")
    public String username;

    public String message;
}
