package com.datechnologies.androidtest.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.datechnologies.androidtest.MainActivity;
import com.datechnologies.androidtest.R;

/**
 * Screen that displays the D & A Technologies logo.
 * The icon can be moved around on the screen as well as animated.
 * */

public class AnimationActivity extends AppCompatActivity {

    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private int xDelta;
    private int yDelta;
    private ImageView logo;
    private MediaPlayer mediaPlayer;

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, AnimationActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        setTitle(R.string.activity_animation_title);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked

        // TODO: When the fade button is clicked, you must animate the D & A Technologies logo.
        // TODO: It should fade from 100% alpha to 0% alpha, and then from 0% alpha to 100% alpha

        // TODO: The user should be able to touch and drag the D & A Technologies logo around the screen.

        // TODO: Add a bonus to make yourself stick out. Music, color, fireworks, explosions!!!

        logo = findViewById(R.id.logo);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.fade);

        logo.setOnTouchListener(new View.OnTouchListener() {
            //RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) logo.getLayoutParams();

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        xDelta = (int) (view.getX() - event.getRawX());
                        yDelta = (int) (view.getY() - event.getRawY());
                        if(mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.setX(xDelta + event.getRawX());
                        view.setY(yDelta + event.getRawY());
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });


        Button fadeButton = (Button) findViewById(R.id.fadeButton);
        fadeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator fader = ObjectAnimator.ofFloat(logo, "alpha", 1.0f, 0);
                fader.setDuration(2000);
                fader.setRepeatCount(1);
                fader.setRepeatMode(ValueAnimator.REVERSE);
                fader.start();
                mediaPlayer.start();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
