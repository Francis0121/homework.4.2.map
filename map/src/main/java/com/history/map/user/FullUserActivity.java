package com.history.map.user;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import com.history.map.R;

public class FullUserActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_user);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        findViewById(R.id.map01Btn).setOnClickListener(this);
        findViewById(R.id.map02Btn).setOnClickListener(this);
        findViewById(R.id.map03Btn).setOnClickListener(this);
        findViewById(R.id.map04Btn).setOnClickListener(this);
        
    }
    
    @Override
    public void onClick(View v) {
        
        Uri gmmIntentUri = null;
    
        switch(v.getId()) {
            case R.id.map01Btn:
                gmmIntentUri = Uri.parse("geo:37.56641923090,126.9778741551?q=천안 독립기념관");
                break;
            case R.id.map02Btn:
                gmmIntentUri = Uri.parse("geo:37.56641923090,126.9778741551?q=전주한옥마을");
                break;
            case R.id.map03Btn:
                gmmIntentUri = Uri.parse("geo:37.56641923090,126.9778741551?q=성산일출봉");
                break;
            case R.id.map04Btn:
                gmmIntentUri = Uri.parse("geo:37.56641923090,126.9778741551?q=서울시청");
                break;
        }
        if(gmmIntentUri != null) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
    }

}
