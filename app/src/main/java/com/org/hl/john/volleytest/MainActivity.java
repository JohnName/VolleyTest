package com.org.hl.john.volleytest;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView textView ;
    TextView textView1 ;
    RequestQueue queue ;
    StringRequest stringRequest ;
    public static final String TAG = "MyTag";

    HttpStack stack ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.mytext);
        textView1 = (TextView) findViewById(R.id.mytext1);
        queue = Volley.newRequestQueue(this);
        String url ="http://www.cnblogs.com/android-host/p/5337435.html";
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                textView.setText("Response is :" + response.substring(0,500));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });
        stringRequest.setTag(TAG);
        queue.add(stringRequest);
        queue.add(obtainWeather());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null){
            queue.cancelAll(TAG);
        }
    }

    private JsonObjectRequest obtainWeather(){
        String url = "http://api.showji.com/Locating/www.showji.com.aspx?m=18782923629";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                textView1.setText("天气预报" + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView1.setText("天气预报" + error.toString());
            }
        });
        return  jsonObjectRequest;
    }

    private void judgeVersion(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){

        }else{

        }
        Network network = new BasicNetwork(stack);
    }

    private void networkCache(){
        RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024);//1mb
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache,network);
        mRequestQueue.start();
        String url = "http://www.baidu.com";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(stringRequest);
    }

}
