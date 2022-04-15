package com.example.meta;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class PhysicalStats extends AppCompatActivity {

    // TextView date;
    EditText vdate;
    EditText weightKg,SleepD, CalorieIn, ExSpan;
    DatePickerDialog.OnDateSetListener setListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.physical_stats);


        weightKg = findViewById(R.id.weightKg);
        SleepD = findViewById(R.id.SleepD);
        CalorieIn = findViewById(R.id.CalorieIn);
        ExSpan = findViewById(R.id.ExSpan);

        // date = findViewById(R.id.date);

        vdate = findViewById(R.id.vdate);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        findViewById(R.id.buttonMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            }
        });

        findViewById(R.id.buttonCheckStatHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), ViewStats.class));
            }
        });

        vdate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PhysicalStats.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
                return false;
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String calDate =dayOfMonth+"/"+month+"/"+year;
                vdate.setText(calDate);
            }
        };

        /* vdate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PhysicalStats.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String calDate = day+"/"+month+"/"+year;
                        vdate.setText(calDate);
                    }
                },year,month,day);
                datePickerDialog.show();

                return false;
            }
        });*/

    }

    public void submit(View view) {

        String url="http://192.168.0.26/userdb/StatsDatabase.php";
        RequestQueue myrequest= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, response -> Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show()
        )


        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> map= new HashMap<>();
                map.put("Weight", weightKg.getText().toString());
                map.put("Hours", SleepD.getText().toString());
                map.put("Calorie_intake", CalorieIn.getText().toString());
                map.put("Daily_workout_time", ExSpan.getText().toString());
                map.put("Date", vdate.getText().toString());
                return map;
            }


        };
        myrequest.add(stringRequest);
    }
}