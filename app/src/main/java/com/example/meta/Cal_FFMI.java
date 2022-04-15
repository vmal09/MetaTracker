package com.example.meta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Cal_FFMI extends AppCompatActivity {

    EditText age,weight,height;
    TextView bfp,ffmi;
    RadioButton mmale, mfemale;
    RadioGroup radioGroupGender1;
    Button mresult;
    float a,w,h,bmimf,bfpmf,ffmimf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_ffmi);

        age=findViewById(R.id.ag);
        weight=findViewById(R.id.wei);
        height=findViewById(R.id.hei);
        //bmi=findViewById(R.id.mass);
        bfp=findViewById(R.id.fat);
        ffmi=findViewById(R.id.ffm);
        mmale=findViewById(R.id.ma);
        mfemale=findViewById(R.id.fema);
        mresult=findViewById(R.id.res);
        radioGroupGender1 = (RadioGroup) findViewById(R.id.rg);

        mmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=Integer.parseInt(age.getText().toString());
                w=Integer.parseInt(weight.getText().toString());
                h=Integer.parseInt(height.getText().toString());

                bmimf=(float) (w/((h/100)*(h/100)));

                bfpmf= (float) ((1.20*bmimf)+(0.23*a)-16.2);

                ffmimf=(float) (bmimf*(1-(bfpmf/100)));
            }
        });

        mfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=Integer.parseInt(age.getText().toString());
                w=Integer.parseInt(weight.getText().toString());
                h=Integer.parseInt(height.getText().toString());

                bmimf=(float) (w/((h/100)*(h/100)));

                bfpmf= (float) ((1.20*bmimf)+(0.23*a)-5.4);

                ffmimf=(float) (bmimf*(1-(bfpmf/100)));
            }
        });

        mresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bmi.setText(""+bmimf);
                bfp.setText(""+bfpmf);
                ffmi.setText(""+ffmimf);
            }
        });

        findViewById(R.id.buttonMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            }
        });


    }
}