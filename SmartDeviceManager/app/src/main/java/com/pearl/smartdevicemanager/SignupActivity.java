package com.pearl.smartdevicemanager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.EmailVerifyListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;



//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖镇楼                  BUG辟易
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；
//                  不见满街漂亮妹，哪个归得程序员？

public class SignupActivity extends Activity {


    private static String TAG = "SignupActivity";

    @BindView(R.id.input_name)
    EditText name_et;
    @BindView(R.id.input_email)
    EditText email_et;
    @BindView(R.id.input_password)
    EditText password_et;
    @BindView(R.id.btn_signup)
    Button signup_btn;
    @BindView(R.id.link_login)
    TextView loginLink_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignUp();
            }
        });

    }

    public void mSignUp(){

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signup_btn.setEnabled(false);

        // 获取用户输入的用户名和密码
        String name = name_et.getText().toString();
        final String email = email_et.getText().toString();
        String password = password_et.getText().toString();

        // 使用BmobSDK提供的注册功能
        BmobUser bu = new BmobUser();
        bu.setUsername(name);
        bu.setPassword(password);
        bu.setEmail(email);
        //注册
        bu.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(SignupActivity.this, "注册成功,请前往"+ email +"邮箱激活", Toast.LENGTH_LONG).show();
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SignupActivity.this.finish();
                    }
                }, 2000);

            }
            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(SignupActivity.this, "注册失败", Toast.LENGTH_LONG).show();
                signup_btn.setEnabled(false);
            }
        });
    }


    //邮箱认证
    public void testEmailVerify(String email){
        BmobUser.requestEmailVerify(this,email, new EmailVerifyListener(){

            @Override
            public void onSuccess() {
                toast("please active your account in your email box");
            }

            @Override
            public void onFailure(int i, String s) {
                toast("email sending failed, please retry"+s);
            }

        });
    }

    public void onSignupSuccess() {
        signup_btn.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signup_btn.setEnabled(true);
    }
    public boolean validate() {
        boolean valid = true;

        String name = name_et.getText().toString();
        String email = email_et.getText().toString();
        String password = password_et.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            name_et.setError("at least 3 characters");
            valid = false;
        } else {
            name_et.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_et.setError("enter a valid email address");
            valid = false;
        } else {
            email_et.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            password_et.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password_et.setError(null);
        }

        return valid;
    }

    public void toast(String info){
        Toast.makeText(getApplicationContext(),info,Toast.LENGTH_SHORT).show();
    }

}
