package com.example.kai.icrowd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

    int width,height;
    File checkPic;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_check_pos );

        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        Log.i( "TEST",width+"and"+height );

        checkPic = new File(this.getExternalFilesDir(null), "pic.jpg");
        Log.i( "TEST","Path:"+checkPic.toString() );

        Bitmap src= BitmapFactory.decodeFile(checkPic.toString());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        params.put("buildingID","1");
        try {
            params.put("image",checkPic);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try{
            client.post("http://saimaa.netlab.hut.fi:5004/location/fine", params, new JsonHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject getPos) {
                    Log.i("TEST","success");
                    if(checkPic.exists())
                    {
                        Log.i( "TEST","Display?" );
//                        LinearLayout Layout = new LinearLayout(getApplicationContext());
//                        setContentView(Layout);
//                        Layout.setOrientation(LinearLayout.VERTICAL);

                        Bitmap myBitmap = BitmapUtils.decodeSampledBitmapFromResource( checkPic.toString(), width, height );
//                        ImageView show_Image = new ImageView( getApplicationContext() );
                        ImageView show_Image = (ImageView)findViewById( R.id.show_image );
                        show_Image.setImageBitmap(myBitmap);
                        mProgressBar.setVisibility( View.GONE);
                        getSupportActionBar().setTitle("Lable Objects");
//                        Layout.addView( show_Image );
                        Log.i( "TEST","Display!" );
                    }
                    try {
                        Toast.makeText( getApplication(), "Position: "+ getPos.getString( "position" ).toString(),Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject object) {
                    super.onFailure(statusCode, headers, throwable, object);
                    Log.i("TEST", "statusCode:"+statusCode);
                    try {
                        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                        setContentView(linearLayout);
                        linearLayout.setOrientation(LinearLayout.VERTICAL);
                        linearLayout.setGravity( Gravity.CENTER );
                        linearLayout.setBackgroundColor( Color.BLACK );

                        TextView tipView = new TextView(getApplicationContext());
                        tipView.setText("LOCATION NOT FOUND! Please continue...");
                        tipView.setTextColor( Color.WHITE);
                        tipView.setTextSize( 30 );
                        tipView.setPadding( 5, 5, 5, 5 );
                        tipView.setGravity( Gravity.CENTER_HORIZONTAL );
                        Typeface tf = Typeface.createFromAsset(getAssets(),"RobotoCondensed-Regular.ttf");
                        tipView.setTypeface( tf );

                        ImageView cryView = new ImageView( getApplicationContext() );
                        cryView.setImageResource(R.drawable.cry);

                        linearLayout.addView( cryView );
                        linearLayout.addView(tipView);
                        mProgressBar.setVisibility( View.GONE);

                        new Handler().postDelayed( new Runnable() {
                            @Override
                            public void run() {
                                Intent mainIntent = new Intent(CheckPos.this, HomeActivity.class);
                                startActivity(mainIntent);
                            }
                        }, 4000);

                        //Toast.makeText( getApplication(), "Code: "+statusCode+"; "+"Message: "+ object.getString("message"),Toast.LENGTH_LONG).show();
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
