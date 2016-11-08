package com.example.kai.icrowd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;

public class CheckPos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_check_pos );

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        int buildingID = 1;
        String url = "http://saimaa.netlab.hut.fi:5004/location/fine";

        File checkPic;
        checkPic = new File(this.getExternalFilesDir(null), "pic.jpg");

        Bitmap src= BitmapFactory.decodeFile(checkPic.toString());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        //params.put("buildingID",Integer.toString(buildingID));
        //params.put("image",imageBytes);
        params.put("buildingID","1");
        try {
            params.put("image",checkPic);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try{
            client.post("http://saimaa.netlab.hut.fi:5004/location/fine", params, new JsonHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                    Log.i("TEST","success");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
                    super.onFailure(statusCode, headers, throwable, object);
                    Log.i("TEST", "statusCode:"+statusCode);
                    try {
                        Toast.makeText( getApplication(), "Code: "+statusCode+"; "+"Message: "+ object.getString("message"),Toast.LENGTH_LONG).show();
                        Log.i("TEST", "message:" + object.getString("message"));
                        Log.i("TEST", "header:" + headers.toString());
                    }catch (JSONException je){
                        Log.i("TEST", je.getMessage());
                    }

                }
            });
        }catch (Exception e){
            Log.i( "TEST",e+" ");
        }
    }

}
