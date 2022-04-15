package com.example.meta;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Remarks extends AppCompatActivity {


    EditText wn1, ns1, tw1, wd1;
    Button buttonMenu;
    Spinner s3;
    int hrs, mins;
    DatePickerDialog.OnDateSetListener setListener3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remarks);

        wn1= findViewById(R.id.editTextPlanName);
        ns1= findViewById(R.id.editTextRemarks);
        wd1=findViewById(R.id.date3);
        tw1= findViewById(R.id.editTextEventTime);
        s3=findViewById(R.id.spinner3);

        Spinner mySpinner=(Spinner) findViewById(R.id.spinner3);
        buttonMenu = (Button) findViewById(R.id.buttonMenu);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Remarks.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.namesss));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        wd1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DatePickerDialog datePickerDialog3=new DatePickerDialog(
                        Remarks.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener3,year,month,day);
                datePickerDialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog3.show();
                return false;
            }
        });


        setListener3=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=dayOfMonth+"/"+month+"/"+year;
                wd1.setText(date);
            }
        };

        findViewById(R.id.buttonMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            }
        });

        findViewById(R.id.buttonViewEventList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), ViewRemarks.class));
            }
        });


        tw1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TimePickerDialog timePickerDialog3=new TimePickerDialog(
                        Remarks.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hrs=hourOfDay;
                                mins=minute;
                                String time=hrs+":"+mins;
                                SimpleDateFormat f24hours= new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date=f24hours.parse(time);
                                    SimpleDateFormat f12hours=new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    tw1.setText(f12hours.format(date));
                                }catch (ParseException e){
                                    e.printStackTrace();
                                }
                            }
                        },12,0,false
                );
                timePickerDialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog3.updateTime(hrs,mins);
                timePickerDialog3.show();
                return false;
            }
        });




    }

    public void submit(View view) {


        String url="http://192.168.0.26/userdb/remarks.php";
        RequestQueue myrequest= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, response -> Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show()
        )


        {


            @Override
            protected Map<String, String> getParams() {
                Map<String,String> map= new HashMap<>();
                map.put("PlanName", wn1.getText().toString());
                map.put("Remarks", ns1.getText().toString());
                map.put("PlanDate", wd1.getText().toString());
                map.put("EventTime", tw1.getText().toString());
                map.put("EventType", s3.getSelectedItem().toString());
                return map;
            }


        };
        myrequest.add(stringRequest);



    }
}
