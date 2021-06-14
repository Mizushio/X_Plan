package com.example.x_plan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.x_plan.utils.HttpUtils;

import org.json.JSONObject;

/**
 *  展示排行榜内容
 */
//todo：整的稍微好看点，字体之类的什么的）
public class RankActivity extends AppCompatActivity {

    private TextView RankingText;
    private ImageView back;
    private String username;
    String result="null";
    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking);
        Intent intent = getIntent();
        this.username = (String)intent.getExtras().get("username");

        init();
        RankingText.setTextSize(16.0F);



    }



    private void init(){
        //todo:格式美化等，后端还有一些数据处理。
        back = findViewById(R.id.back);
        RankingText=findViewById(R.id.rankText);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String rank="";
                    //本来get就行的。随便post点东西，都一样）
                    String pamm="{\"username\":\""+"get"+"\",\"password\":\""+"passwordText"+"\"}";
                    result=(HttpUtils.sendPost("http://mizushio.top:8080/AppGetrank",pamm));
                    result=result.replaceAll("分","分\n");
                    RankingText.setText(result);
                } catch (Exception e) {
                    e.printStackTrace();
                    RankingText.setText("获取失败");
                }
            }
        }).start();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putString("username",username);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });



    }

}
