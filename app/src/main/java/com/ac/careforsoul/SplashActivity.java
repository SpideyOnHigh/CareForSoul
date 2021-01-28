package com.ac.careforsoul;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {

    ImageView imageView;
    Animation bounce;
//    String prn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.actiity_splash);
        imageView = (ImageView)findViewById(R.id.img);
        Paper.init(this);
//        prn = Paper.get(Common.USER_KEY);
//        String user = Paper.book().read(Common.USER_KEY);
//        String pwd = Paper.book().read(Common.PWD_KEY);
//        if(user != null && pwd != null)
//        {
//            if(!user.isEmpty() && !pwd.isEmpty())
//                login();
//        }
        setStatusBarColor(R.color.main);
        //init paper


        super.onCreate(savedInstanceState);

        bounce = AnimationUtils.loadAnimation(this,R.anim.bounce);
        imageView.startAnimation(bounce);

        new Handler().postDelayed(new Runnable() {

     public void run() {
                // This method will be executed once the timer is over

                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

        //check remember

    }
    public void setStatusBarColor(int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, colorResId));
        }
    }

//    private void login() {
//        if (Common.isConnectedToInternet(getBaseContext())) {
//
//            Intent intent =  new Intent(SplashActivity.this,HomeActivity.class);
//            intent.putExtra("prn",prn);
//            startActivity(intent);
//
//        } else {
//            Toast.makeText(SplashActivity.this, "Please Check Your Network Connection!!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//    }
}





