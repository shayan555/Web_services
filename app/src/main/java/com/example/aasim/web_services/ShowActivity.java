package com.example.aasim.web_services;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {
 ListView listView;
    String url="https://mohdaasim71.000webhostapp.com/getdata.php";
    String jsonData;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView= (ListView) findViewById(R.id.list);
        arrayList=new ArrayList<>();

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);
new getRecord().execute();
    }

    class  getRecord extends AsyncTask<String,String,String>
    {

       ProgressDialog pd;
        @Override
        protected void onPreExecute() {
           pd=new ProgressDialog(ShowActivity.this);
            pd.setMessage("downloading");
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            DefaultHttpClient httpClient=new DefaultHttpClient(new BasicHttpParams());
            HttpPost httpPost=new HttpPost(url);
            //depends on your web service
            httpPost.setHeader("Content_type","application/json");

            InputStream inputStream=null;
            String result=null;

            try
            {

                 HttpResponse response=httpClient.execute(httpPost);
                HttpEntity entity=response.getEntity();

                inputStream=entity.getContent();


                //json is UTF 8 by default

                BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"),8);
                StringBuilder sb=new StringBuilder();

                String line=null;
                while ((line=reader.readLine())!=null)
                {
                    sb.append(line+"\n");
                }
                result=sb.toString();



            }
            catch (Exception e)
            {}
            finally
            {
            try
            {
                if(inputStream!=null) inputStream.close();
            }
            catch (Exception e){}

            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
           jsonData=s;
            //calling if jadu method
            jadu();
            pd.dismiss();


            super.onPostExecute(s);
        }
    }

    public  void jadu()
    {
        //jason parsing
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
             JSONArray jsonArray=jsonObject.getJSONArray("result");//result is the name of array name in web service

for (int i=0;i<jsonArray.length();i++)
{
    JSONObject obj=jsonArray.getJSONObject(i);
    int id=obj.getInt("id");
    String name=obj.getString("name");
    String address=obj.getString("address");
    String mobile=obj.getString("mobile");

    arrayList.add("Id:-"+id+"\nName:-"+name+"\nAddress:-"+address+"\nMobile:-"+mobile);
    adapter.notifyDataSetChanged();
}



           }
        catch (Exception e){}
    }
}
