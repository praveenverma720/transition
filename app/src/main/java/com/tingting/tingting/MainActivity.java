package com.tingting.tingting;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;
    private int position = 0;
    private LinearLayout linearLayout;
    private Handler handler, handler1;
    private Runnable runnable, runnable1;
    private Resources resources;
    private RelativeLayout relativeLayout;
    private CardView cardView, cardView_2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        videoView = (VideoView) findViewById(R.id.videoView);
        cardView = (CardView) findViewById(R.id.cardView);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        cardView_2 = (CardView) findViewById(R.id.cardView_2);

        handler = new Handler();
        handler1 = new Handler();

        resources = this.getResources();

        try {
            int id_1 = this.getRawResIdByName("rawvideo");
            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id_1));

            videoView.setFitsSystemWindows(true);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoView.requestFocus();

        videoView.start();


        changeBackgroundColour();

    }


    private int getRawResIdByName(String resName) {
        String pkgName = this.getPackageName();

        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
        Log.i("AndroidVideoView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Store current position.
        savedInstanceState.putInt("CurrentPosition", videoView.getCurrentPosition());
        videoView.pause();
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get saved position.
        position = savedInstanceState.getInt("CurrentPosition");
        videoView.seekTo(position);
    }

    private void changeBackgroundColour()
    {


        cardView.setVisibility(View.INVISIBLE);

        linearLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        cardView_2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        videoView.setLayoutParams(new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.MATCH_PARENT, Gravity.CENTER));

        //  Transparent Background
        linearLayout.setBackgroundColor(0);
        cardView_2.setCardBackgroundColor(0);
        relativeLayout.setBackgroundColor(0);

        Log.d("Position 1",""+position);

        runnable = new Runnable() {
            @Override
            public void run() {

                if(!videoView.isPlaying())
                {
                    stopHandlers();
                }
                else {
                    changeBackgroundColour_TV2();
                }

            }
        };

        handler.postDelayed(runnable, 5000);

    }

    private void changeBackgroundColour_TV2()
    {


        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                        300, resources.getDisplayMetrics());

        int height = this.generateRandomNos();

        Log.d("Height and Width","Height: " + height +" "+ "Width: " + width);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            relativeLayout.setBackground(ContextCompat.getDrawable(this,R.drawable.gradient));
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);


        int marginTop = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                100,
                resources.getDisplayMetrics()
        );


        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.setMargins(0, marginTop,0,0);

        linearLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                         RelativeLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setBackgroundColor(ContextCompat.getColor(this,R.color.colorCustom));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            linearLayout.setBackground(ContextCompat.getDrawable(this,R.drawable.halfcircle));
        }
        linearLayout.setLayoutParams(params);


        int height_cardView = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                75,
                resources.getDisplayMetrics()
        );

        int width_cardView =(int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                75,
                resources.getDisplayMetrics()
        );

        cardView_2.setLayoutParams(new LinearLayout.LayoutParams(height_cardView,width_cardView));
        cardView_2.setCardBackgroundColor(0);


        videoView.setLayoutParams(new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.MATCH_PARENT, Gravity.CENTER));



        RelativeLayout.LayoutParams params_card_view = new RelativeLayout.LayoutParams(
                width,
                height);

        params_card_view.addRule(RelativeLayout.BELOW, R.id.linearLayout);
        params_card_view.addRule(RelativeLayout.CENTER_HORIZONTAL);


        cardView.setLayoutParams(params_card_view);
        cardView.setVisibility(View.VISIBLE);


        runnable1 = new Runnable() {
            @Override
            public void run() {

                if(!videoView.isPlaying()) {

                    stopHandlers();
                }
                else
                    {
                        changeBackgroundColour();
                    }



            }
        };

        handler1.postDelayed(runnable1, 5000);

    }

    private void stopHandlers()
    {
        handler.removeCallbacks(runnable);
        handler1.removeCallbacks(runnable1);
        videoView.stopPlayback();
    }

   private int generateRandomNos()
   {
       int low = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
               300, resources.getDisplayMetrics());

       int high = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
               500, resources.getDisplayMetrics());


       Random random = new Random();

       int num = low + random.nextInt(high - low);

       return num;
   }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();


    }

    @Override
    protected void onResume() {
        super.onResume();


        //videoView.resume();
        Log.d("Position","onResume() : " + position);
        videoView.seekTo(position);
        //videoView.resume();
        videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        videoView.pause();
        position = videoView.getCurrentPosition();


        Log.d("Position","onPause() : " + position);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopHandlers();
    }
}
