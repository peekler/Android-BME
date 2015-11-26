package hu.bme.aut.android.aitchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.login_progress)
    protected ProgressBar login_progress;

    @Bind(R.id.etUserName)
    protected EditText etUserName;

    @Bind(R.id.etPassword)
    protected EditText etPassword;

    @Bind(R.id.btnRegister)
    protected Button btnRegister;

    @Bind(R.id.btnLogin)
    protected Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        ParseUser.logOut();
    }

    @OnClick(R.id.btnRegister)
    protected void registerUser() {
        ParseUser user = new ParseUser();
        user.setUsername(etUserName.getText().toString());
        user.setPassword(etPassword.getText().toString());


        login_progress.setVisibility(View.VISIBLE);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                login_progress.setVisibility(View.GONE);

                if (e == null) {
                    Toast.makeText(LoginActivity.this, "Registration OK",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Registration failed: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick(R.id.btnLogin)
    protected void loginUser() {
        login_progress.setVisibility(View.VISIBLE);
        ParseUser.logInInBackground(etUserName.getText().toString(),
                etPassword.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                login_progress.setVisibility(View.GONE);
                if (e == null) {
                    Toast.makeText(LoginActivity.this,
                            "Login Success",
                            Toast.LENGTH_SHORT).show();

                    Intent intentStartMainActivity = new Intent(
                            LoginActivity.this, MainActivity.class);
                    startActivity(intentStartMainActivity);
                    // close this activity
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Login failed: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
