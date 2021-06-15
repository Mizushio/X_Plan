package com.example.x_plan;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.text.TextUtils;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.x_plan.utils.HttpUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;

public class SignActivity extends AppCompatActivity {
    private Button sign,register;
    private EditText username,password;
    private String usernameText,passwordText;
    private TextView msg;

    @Override
    protected  void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();
        getInfo(this);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameText=username.getText().toString().trim();
                passwordText=password.getText().toString().trim();
                if(TextUtils.isEmpty(usernameText)){
                    msg.setText("请输入用户名");
                    return;
                }
                else if(TextUtils.isEmpty(passwordText)){
                    msg.setText("请输入密码");
                    return;
                }
                else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String resCode;
                                String utf8Username= URLDecoder.decode(usernameText,"UTF-8");
                                String pamm="{\"username\":\""+utf8Username+"\",\"password\":\""+passwordText+"\"}";
                                String result=(HttpUtils.sendPost("http://mizushio.top:8080/AppLogin",pamm));
                                JSONObject jsonObject=new JSONObject(result);
                                resCode=jsonObject.getString("code");
                                if(resCode.equals("200")){
                                    msg.setText("登录成功");
                                    //登录成功后进入主界面
                                    saveLoginStatus(utf8Username,passwordText);
                                    // TODO: 2021/6/2 使用intent传递对象
                                    Intent intent1=new Intent(SignActivity.this,MainActivity.class);
                                    intent1.putExtra("username",utf8Username);
                                    SignActivity.this.finish();
                                    startActivity(intent1);
                                }
                                else if(resCode.equals("401")){ msg.setText("用户名不存在"); }
                                else if(resCode.equals("402")){ msg.setText("密码错误，请重试"); }
                            } catch (JSONException | UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                usernameText=username.getText().toString().trim();
                passwordText=password.getText().toString().trim();
                if(TextUtils.isEmpty(usernameText)){
                    msg.setText("请输入用户名");
                    return;
                }
                else if(TextUtils.isEmpty(passwordText)){
                    msg.setText("请输入密码");
                    return;
                }
                else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String resCode;
                                String utf8Username= URLDecoder.decode(usernameText,"UTF-8");
                                String pamm="{\"username\":\""+utf8Username+"\",\"password\":\""+passwordText+"\"}";
                                String result=(HttpUtils.sendPost("http://mizushio.top:8080/AppRegister",pamm));
                                JSONObject jsonObject=new JSONObject(result);
                                resCode=jsonObject.getString("code");
                                if(resCode.equals("200")) {
                                    msg.setText("注册成功，请进行登录");
                                    username.setText("");
                                    password.setText("");
                                    return;
                                }
                                else if(resCode.equals("401")){ msg.setText("用户名已存在"); }
                            } catch (JSONException | UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
    }

    private void init(){
        sign=findViewById(R.id.login);
        register=findViewById(R.id.register);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        msg=findViewById(R.id.msg);
    }

    private void getInfo(Context context){
        SharedPreferences sp=getSharedPreferences("UserInfo",MODE_PRIVATE);
        String username_=sp.getString("username",null);
        String password_=sp.getString("password",null);
        username.setText(username_);
        password.setText(password_);

    }

    private void saveLoginStatus(String username,String password){
        SharedPreferences sp=getSharedPreferences("UserInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("username",username);
        editor.putString("password",password);
        editor.commit();
    }
}
