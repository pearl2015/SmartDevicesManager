package com.pearl.smartdevicemanager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.CheckBox;
import com.pearl.smartdevicemanager.beans.IoTUser;
import com.pearl.smartdevicemanager.main.DrawerActivity;
import com.pearl.smartdevicemanager.tools.Tools;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;


public class LoginActivity extends Activity implements View.OnClickListener, CheckBox.OnCheckListener {

    @BindView(R.id.input_email) EditText email_et;
    @BindView(R.id.input_password) EditText password_et;
    @BindView(R.id.login_btn) Button login_btn;
    @BindView(R.id.link_signup) TextView signup_tv;
    @BindView(R.id.auto_login_check) CheckBox auto_login_check;
    @BindView(R.id.password_check) CheckBox password_check;

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private ArrayList<IoTUser> userlist;
    private SharedPreferences sp;

    private String email;
    private String password;

    private boolean account_check;
    private boolean auto_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Bmob.initialize(this, "bmob中的key");

        init();

    }

    public void init(){
        //添加注入框架和Bmob应用的密钥

        login_btn.setOnClickListener(this);
        signup_tv.setOnClickListener(this);

        auto_login_check.setOncheckListener(this);
        password_check.setOncheckListener(this);

        //记住用户偏好
        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);

        account_check = sp.getBoolean("account_check",false);
        auto_check = sp.getBoolean("auto_login_check",false);

        //如果用户上次登录操作选择了记住用户
        if(account_check)
        {
            email_et.setText(sp.getString("email",""));
            password_et.setText(sp.getString("password",""));
            password_check.setChecked(true);
        }


        //如果用户上次登录选择了自动登录
        if(auto_check)
        {
            auto_login_check.setChecked(true);
            email = sp.getString("email","");
            password = sp.getString("password","");
            mLogin();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
                this.finish();
            }
        }
    }
    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
       // moveTaskToBack(true);
    }


    public void mLogin(){

   //    检测用户输入
        if(!validate()) {
            onLoginFailed();
            return;
        }

        login_btn.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        BmobUser user = new BmobUser();
        user.setUsername(email);
        user.setPassword(password);

        user.login(this,new SaveListener(){

            @Override
            public void onSuccess() {
                Log.e("TAG", "SUCCESS");
               // intent.setClass(LoginActivity.this, DrawerActivity.class);

                //得到用户信息
                String xUsername= "";
                String email="";
                BmobUser bmobUser = BmobUser.getCurrentUser(getApplicationContext());
                if(bmobUser != null){
                    // 允许用户使用应用
                    xUsername=bmobUser.getUsername();
                    email = bmobUser.getEmail();
                    Log.e("email", xUsername);

                }else{
                    //缓存用户对象为空时， 可打开用户注册界面…
                    Log.e("email", "Unknown......");
                }

                IoTUser localuser = new IoTUser();
                localuser.setUsername(xUsername);
                localuser.setEmail(email);
                localuser.setPassword(""); //防止出错？

                Bundle bundle = new Bundle();
                bundle.putSerializable("user_info", localuser);
                Intent intent = new Intent(LoginActivity.this, DrawerActivity.class);
                intent.putExtras(bundle);

 //               intent.putExtra("loginInfo",xUsername);

                progressDialog.dismiss();
                onLoginSuccess();
                LoginActivity.this.startActivity(intent);
                finish();
            }
            @Override
            public void onFailure(int i, String s) {
                Log.e(i+"", s);
                Log.e("TAG", "failure2");
                progressDialog.dismiss();
                onLoginFailed();
            }
        });
    }


    //登录失败
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        login_btn.setEnabled(true);
        email_et.setText("");
        password_et.setText("");

        return;
    }

    public void onLoginSuccess() {
        login_btn.setEnabled(true);
       // finish();
    }

    public boolean validate() {
        boolean valid = true;

        String email = email_et.getText().toString();
        String password = password_et.getText().toString();

//        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            email_et.setError("enter a valid email address");
//            valid = false;
//        } else {
//            email_et.setError(null);
//        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            password_et.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password_et.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_btn:
                email = email_et.getText().toString();
                password = password_et.getText().toString();
                //检查是否用户选择保存邮箱和密码
                if(account_check){
                    Tools.writetoSharedPreferences(sp,email,password);
                }
                mLogin();
                break;
            case R.id.link_signup:
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                break;
            default:
                break;
        }
    }
    @Override
    public void onCheck(CheckBox checkBox, boolean b) {
        switch (checkBox.getId()){
            case R.id.password_check:  //记住用户名和密码
                account_check = password_check.isCheck();
                Tools.writeCheckStatetoSharedPreferences(sp,"account_check", account_check);
                break;
            case R.id.auto_login_check: //下次自动登录
                auto_check = auto_login_check.isCheck();
                Tools.writeCheckStatetoSharedPreferences(sp,"auto_login_check", auto_check);

                password_check.setChecked(true);
                account_check = password_check.isCheck();
                Tools.writeCheckStatetoSharedPreferences(sp,"account_check", account_check);

                break;
            default:
                break;
        }
    }
}
