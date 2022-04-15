package com.example.meta;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

public class ViewWorkout extends AppCompatActivity {


    ListView listView;
    ArrayAdapter<String> adapter;
    Button buttonAddSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_workout);

        buttonAddSession = (Button) findViewById(R.id.buttonAddSession);

        ListView listView=(ListView) findViewById(R.id.listView);
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        new Connection().execute();

        findViewById(R.id.buttonAddSession).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), CreateWorkout.class));
            }
        });

    }

    class Connection extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            String result="";
            String host="http://192.168.0.26/userdb/getdata.php";
            try{
                HttpClient client=new DefaultHttpClient();
                HttpGet request= new HttpGet();
                request.setURI(new URI(host));
                HttpResponse response = client.execute(request);
                BufferedReader reader= new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer stringBuffer= new StringBuffer("");
                String line="";
                while((line=reader.readLine()) !=null){
                    stringBuffer.append(line);
                    break;
                }
                reader.close();
                result=stringBuffer.toString();
            }catch (Exception e){
                return new String("There exception: "+e.getMessage());
            }

            return result;
        }
        @Override
        protected  void onPostExecute(String result){
            try {
                JSONObject jsonResult= new JSONObject(result);
                int success= jsonResult.getInt("success");
                if(success==1){
                    JSONArray s=jsonResult.getJSONArray("s");
                    for(int i=0; i<s.length();i++){
                        JSONObject sessions=s.getJSONObject(i);
                        int wid=sessions.getInt("id");
                        String wn=sessions.getString("ExcerciseName");
                        String ws=sessions.getString("No_Set");
                        String wd=sessions.getString("WorkoutDate");
                        String wt=sessions.getString("WorkoutTime");
                        String wtype=sessions.getString("workout_type");

                        String line= wid+"."+"  Session Name: "+wn+"        \n     Workout Type:  "+wtype+"   \n     Date: "+wd+"   \n     Time: "+wt+" \n     Sets:  "+ws+"\n";

                        adapter.add(line);
                    }



                    Toast.makeText(getApplicationContext(), "The workout session is in display",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "No workout session created!! Recommend create new session",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

    }
}