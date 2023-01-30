package com.datechnologies.androidtest.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.api.PostLoginService;
import com.datechnologies.androidtest.api.RetroLoginResponse;
import com.datechnologies.androidtest.api.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A screen that displays a login prompt, allowing the user to login to the D & A Technologies Web Server.
 *
 */
public class LoginActivity extends AppCompatActivity {

    public static final String LOG_TAG = LoginActivity.class.getSimpleName();
    private Long startTime;
    private final int LOGIN_CODE_SUCCESS = 200;   //The HTTP 200 OK success status response code indicates that the request has succeeded

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle(R.string.activity_login_title);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked
        // TODO: Save screen state on screen rotation, inputted username and password should not disappear on screen rotation

        // TODO: Send 'email' and 'password' to http://dev.rapptrlabs.com/Tests/scripts/login.php
        // TODO: as FormUrlEncoded parameters.

        // TODO: When you receive a response from the login endpoint, display an AlertDialog.
        // TODO: The AlertDialog should display the 'code' and 'message' that was returned by the endpoint.
        // TODO: The AlertDialog should also display how long the API call took in milliseconds.
        // TODO: When a login is successful, tapping 'OK' on the AlertDialog should bring us back to the MainActivity

        // TODO: The only valid login credentials are:
        // TODO: email: info@rapptrlabs.com
        // TODO: password: Test123
        // TODO: so please use those to test the login.
    }

    public void onLoginClicked(View v) {

        EditText emailText = findViewById(R.id.username);
        EditText passwordText = findViewById(R.id.password);
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if(!email.equals("") && !password.equals("")) {
            startTime = System.currentTimeMillis();
            PostLoginService postLoginService = RetrofitClient.getInstance().create(PostLoginService.class);
            Call<RetroLoginResponse> call = postLoginService.login(email, password);

            call.enqueue(new Callback<RetroLoginResponse>() {
                @Override
                public void onResponse(Call<RetroLoginResponse> call, Response<RetroLoginResponse> response) {
                    Long endTime = System.currentTimeMillis();
                    Long responseTime = endTime - startTime;

                    if(response.code() == LOGIN_CODE_SUCCESS) {
                        // login success
                        RetroLoginResponse loginResponse = response.body();
                        String code = loginResponse.getCode();
                        String message = loginResponse.getMessage();

                        showAlertDialog(code, message, responseTime);
                    } else {
                        // We got an unsuccessful response back
                        ResponseBody responseBody = response.errorBody();
                        try {
                            String errorResponse = responseBody.string();
                            Log.i(LOG_TAG, errorResponse);
                            JSONObject errorResponseJSON = new JSONObject(errorResponse);
                            String code = errorResponseJSON.getString("code");
                            String message = errorResponseJSON.getString("message");
                            showAlertDialog(code, message, responseTime);
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RetroLoginResponse> call, Throwable t) {
                    Log.e(LOG_TAG, "Login request failed");
                    Log.e(LOG_TAG, t.getLocalizedMessage());
                }
            });
        }
        else
        {
            Toast.makeText(this, "Please enter a valid email and password.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAlertDialog(final String code, String message, Long responseTime) {
        new AlertDialog.Builder(this)
                .setTitle(code)
                .setMessage(message + " (API Call took " + responseTime + " ms)")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (code.equals("Success")) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
