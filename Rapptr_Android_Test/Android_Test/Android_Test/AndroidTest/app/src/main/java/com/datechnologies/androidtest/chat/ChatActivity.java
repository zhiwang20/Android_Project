package com.datechnologies.androidtest.chat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.api.ChatLogMessageModel;

import java.util.List;

import com.datechnologies.androidtest.api.GetDataService;
import com.datechnologies.androidtest.api.RetroDataResponse;
import com.datechnologies.androidtest.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Screen that displays a list of chats from a chat log.
 */
public class ChatActivity extends AppCompatActivity {

    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    public static final String LOG_TAG = ChatActivity.class.getSimpleName();

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, ChatActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        setTitle(R.string.activity_chat_title);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        chatAdapter = new ChatAdapter();

        recyclerView.setAdapter(chatAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false));

        //List<ChatLogMessageModel> tempList = new ArrayList<>();

        //ChatLogMessageModel chatLogMessageModel = new ChatLogMessageModel();
        //chatLogMessageModel.message = "This is test data. Please retrieve real data.";

        //tempList.add(chatLogMessageModel);
        //tempList.add(chatLogMessageModel);
        //tempList.add(chatLogMessageModel);
        //tempList.add(chatLogMessageModel);
        //tempList.add(chatLogMessageModel);
        //tempList.add(chatLogMessageModel);
        //tempList.add(chatLogMessageModel);
        //tempList.add(chatLogMessageModel);

        //chatAdapter.setChatLogMessageModelList(tempList);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Retrieve the chat data from http://dev.rapptrlabs.com/Tests/scripts/chat_log.php
        // TODO: Parse this chat data from JSON into ChatLogMessageModel and display it.

        getMessagesRequest();
    }

    private void getMessagesRequest() {
        GetDataService getDataService = RetrofitClient.getInstance().create(GetDataService.class);
        Call<RetroDataResponse> call = getDataService.getResponse();

        call.enqueue(new Callback<RetroDataResponse>() {
            @Override
            public void onResponse(Call<RetroDataResponse> call, Response<RetroDataResponse> response) {
                handleResponse(response.body());
            }

            @Override
            public void onFailure(Call<RetroDataResponse> call, Throwable t) {
                Log.d(LOG_TAG, "Get request failed");
            }
        });
    }

    private void handleResponse(RetroDataResponse retroDataResponse) {
        List<ChatLogMessageModel> messagesList = retroDataResponse.getData();
        chatAdapter.setChatLogMessageModelList(messagesList);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
