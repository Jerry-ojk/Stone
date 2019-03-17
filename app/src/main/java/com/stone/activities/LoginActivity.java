package com.stone.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.stone.R;
import com.stone.views.LoginButton;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private UserLoginTask mAuthTask = null;

    // UI references.
    private TextInputLayout userNameLayout;
    private TextInputLayout passwordLayout;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Utils.setStatusBarDark(this);
        userNameLayout = findViewById(R.id.username_layout);
        passwordLayout = findViewById(R.id.password_layout);

        passwordLayout.getEditText().setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        String reName = preferences.getString("reName", null);
        String rePass = preferences.getString("rePass", null);
        if (reName != null && rePass != null) {
            userNameLayout.getEditText().setText(reName);
            passwordLayout.getEditText().setText(rePass);
        } else {
            userNameLayout.requestFocus();
        }
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        Toast.makeText(this, "测试密码和账户都是123456", Toast.LENGTH_LONG).show();
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        userNameLayout.setError(null);
        passwordLayout.setError(null);

        String uerName = userNameLayout.getEditText().getText().toString();
        String password = passwordLayout.getEditText().getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(uerName)) {
            userNameLayout.setError("用户名为空！");
            focusView = userNameLayout;
            cancel = true;
        } else if (12 < password.length() || password.length() < 6) {
            passwordLayout.setError("密码长度不对！");
            focusView = passwordLayout;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            loginButton.start();
            mAuthTask = new UserLoginTask(uerName, password);
            mAuthTask.execute((Void) null);
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Integer> {

        private final String userName;
        private final String password;

        UserLoginTask(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return 0;
            }

            if (userName.equals("123456") && password.equals("123456")) {
                return 1;
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer code) {
            mAuthTask = null;

            switch (code) {
                case 0:
                    loginButton.fail();
                    passwordLayout.setError("用户名或密码错误！");
                    passwordLayout.requestFocus();
                    break;
                case 1:
                    SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                    editor.putString("reName", userName);
                    editor.putString("rePass", password);
                    editor.apply();
                    loginButton.success();
                    Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("userName", "Jerry");
                    setResult(1, intent);
                    finish();
                    break;
                case 2:
                    loginButton.fail();
                    userNameLayout.setError("用户名不存在！");
                    userNameLayout.requestFocus();
                    break;
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            loginButton.cancel();
        }
    }
}

