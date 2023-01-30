

package com.example.localization_for_lunarlander;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


//findViewById didn't work when I was using SimpleActivity
//view.findViewById(R.id.mycanvas) can get rid of red error but still onClick button --->
//Null Pointer Exception: Attempt to invoke virtual method on a null object reference
//java.lang.IllegalStateException: Could not execute method for android:onClick

public class LanderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lander);

    }

    //Calling findViewById() on the Activity object will only work if the current Activity layout is set by setContentView
    public void playClick(View view) {
        LanderCanvas canvas = (LanderCanvas) findViewById(R.id.mycanvas);
        canvas.startGame();
    }

    public void stopClick(View view) {
        LanderCanvas canvas = (LanderCanvas) findViewById(R.id.mycanvas);
        canvas.stopGame();
    }

}
