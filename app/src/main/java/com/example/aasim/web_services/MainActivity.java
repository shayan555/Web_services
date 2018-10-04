package com.example.aasim.web_services;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText et_name,et_address,et_mobile;
    Button b1,b2;
    String url="https://mohdaasim71.000webhostapp.com/senddata.php";
    String name,address ;
    String mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //useLibrary 'org.apache.http.legacy'
        //add this library in the app module in gradle for web services

        et_address= (EditText) findViewById(R.id.editText);
        et_name= (EditText) findViewById(R.id.editText2);
        et_mobile= (EditText) findViewById(R.id.editText4);
        b1= (Button) findViewById(R.id.button);
        b2= (Button) findViewById(R.id.button2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             name=et_name.getText().toString();
                address=et_address.getText().toString();
                mobile=et_mobile.getText().toString();

                new setRecord().execute();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ShowActivity.class);
                startActivity(intent);
            }
        });
    }
    class setRecord extends AsyncTask<String,String,String>
    {

        ProgressDialog pd;

        @Override
        protected void onPreExecute()
        {
            pd=new ProgressDialog(MainActivity.this);
            pd.setMessage("uploading...");
            pd.show();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params)
        {
            try {
                ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();

                list.add(new BasicNameValuePair("name", name));
                list.add(new BasicNameValuePair("address", address));
                list.add(new BasicNameValuePair("mobile", mobile));

                DefaultHttpClient httpClient = new DefaultHttpClient();   //send the request

                HttpPost httpPost = new HttpPost(url);  //

                httpPost.setEntity(new UrlEncodedFormEntity(list));

                httpClient.execute(httpPost);
            }

            catch (Exception e)
            {
                Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {

            pd.dismiss();
            et_name.setText("");
            et_address.setText("");
            et_mobile.setText("");
            Toast.makeText(MainActivity.this, "data saved", Toast.LENGTH_SHORT).show();
            super.onPostExecute(s);
        }

    }

}
