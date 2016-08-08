package com.pearl.bmobtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gc.materialdesign.views.CheckBox;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MaterialActivity extends AppCompatActivity {

    @BindView(R.id.checkBox) CheckBox test_box;
    @BindView(R.id.test_btn) Button test_btn;


    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        ButterKnife.bind(this);
        //记住用户偏好
        sp = MaterialActivity.this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                test_box.setChecked(sp.getBoolean("check",false));
            }
        },300);



        test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences spw = sp;
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("check", !sp.getBoolean("check",false));
                editor.commit();
            }
        });

    }
}
