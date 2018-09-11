package com.awccis.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.awccis.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private EditText mEdtLoginPwd;
    private Button mBtnLogin;
    private CheckBox mCheckRemeberPwd;
    private CheckBox mCheckAutoLogin;
    private CheckedTextView mBtnRegister;
    private CheckedTextView mBtnForgetPwd;
    private EditText mEdtLoginId;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    /**
     * 初始化组件和数据
     */
    private void initView() {
        mEdtLoginPwd = (EditText) findViewById(R.id.edt_login_pwd);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mCheckRemeberPwd = (CheckBox) findViewById(R.id.check_remeber_pwd);
        mCheckAutoLogin = (CheckBox) findViewById(R.id.check_auto_login);
        mBtnRegister = (CheckedTextView) findViewById(R.id.btn_to_register);
        mBtnForgetPwd = (CheckedTextView) findViewById(R.id.btn_forget_pwd);
        mEdtLoginId = (EditText) findViewById(R.id.edt_login_id);

        mBtnLogin.setOnClickListener(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean isRemember = sharedPreferences.getBoolean("rememberPwd", false);
        if (isRemember){
            String loginId = sharedPreferences.getString("loginId","");
            String loginPwd = sharedPreferences.getString("loginPwd", "");
            mEdtLoginId.setText(loginId);
            mEdtLoginPwd.setText(loginPwd);
            mCheckRemeberPwd.setChecked(true);
        }
    }

    /**
     * 监听事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (submit()){
                    if (mCheckRemeberPwd.isChecked()){
                        editor = sharedPreferences.edit();
                        editor.putString("loginId", mEdtLoginId.getText().toString().trim());
                        editor.putString("loginPwd", mEdtLoginPwd.getText().toString().trim());
                        editor.putBoolean("rememberPwd", true);
                    }else {
                        editor.clear();
                    }
                    editor.apply();
                    //进入主页面
                }else {
                    Log.w(TAG, "onClick: 登录按钮");
                    Toast.makeText(this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_to_register:

                break;
            case  R.id.btn_forget_pwd:

                break;
            case R.id.check_remeber_pwd:

                break;
            case R.id.check_auto_login:

                break;
        }
    }

    /**
     * 登录
     */
    private boolean submit() {
        // validate
        String pwd = mEdtLoginPwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "pwd不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        String id = mEdtLoginId.getText().toString().trim();
        if (TextUtils.isEmpty(id)) {
            Toast.makeText(this, "id不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        // TODO validate success, do something
        //String loginId = sharedPreferences.getString("loginId", "");
        //String loginPwd = sharedPreferences.getString("loginPwd", "");
        String loginId = "txp";
        String loginPwd = "123";
        if(loginId.equals(id) && loginPwd.equals(pwd)){
            Toast.makeText(this, "登陆成功！", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    /**
     * 启动登录页面并且携带登录信息
     * @param context context
     * @param loginId loginId
     * @param loginPwd loginPwd
     */
    public static void actionLoginActivity(Context context, String loginId, String loginPwd){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("loginId", loginId);
        intent.putExtra("loginPwd", loginPwd);
        context.startActivity(intent);
    }
}
