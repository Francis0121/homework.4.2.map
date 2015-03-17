package com.history.map.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import com.history.map.R;
import com.history.map.position.MapsActivity;

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
        
        String str = null;
        String uri = null;
    
        switch(v.getId()) {
            case R.id.map01Btn:
                str = "천안 독립기념관";
                uri = "http://m.map.naver.com/siteview.nhn?code=11620331";
                break;
            case R.id.map02Btn:
                str = "전주한옥마을";
                uri = "http://m.map.naver.com/siteview.nhn?code=13208779";
                break;
            case R.id.map03Btn:
                str = "성산일출봉";
                uri = "http://m.map.naver.com/siteview.nhn?code=11491438";
                break;
            case R.id.map04Btn:
                str = "서울시청";
                uri = "http://m.map.naver.com/siteview.nhn?code=11556036";
                break;
        }
        if(str != null && uri != null) {
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("uri", uri);
            intent.putExtra("str", str);
            
            startActivity(intent);
        }
    }

}
