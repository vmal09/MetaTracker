package com.example.meta;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StopWatch extends AppCompatActivity {

    private int seconds=0;
    private boolean running;

    Button buttonMenu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stop_watch);
        startTimer();
        buttonMenu = (Button) findViewById(R.id.buttonMenu);




        findViewById(R.id.buttonMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            }
        });
    }

    public void onstart(View view){
        running=true;
    }
    public void onstop(View view){
        running=false;
    }
    public void onreset(View view){
        running=false;
        seconds=0;
    }
    private void startTimer () {
        final TextView timer=findViewById(R.id.timer);
        Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hrs=seconds/3600;
                int mins=(seconds%3600)/60;
                int sec=seconds%60;
                String time=String.format("%02d:%02d:%02d",hrs,mins,sec);
                timer.setText(time);
                if(running){
                    seconds++;
                }
                handler.postDelayed(this, 0);
            }
        });

    }


}
