package com.example.brandon.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class LogInActivity extends ActionBarActivity {

    protected TextView signUpTextView;
    protected EditText username;
    protected EditText password;
    protected Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_log_in);

        signUpTextView = (TextView)findViewById(R.id.signUpText);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        username = (EditText)findViewById(R.id.usernameField);
        password = (EditText)findViewById(R.id.passwordField);
        loginButton = (Button)findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();

                usernameText = usernameText.trim();
                passwordText = passwordText.trim();

                if (usernameText.isEmpty() || passwordText.isEmpty()) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LogInActivity.this);
                    alertBuilder.setMessage(R.string.login_error_message);
                    alertBuilder.setTitle(R.string.oh_no_error_title);
                    alertBuilder.setPositiveButton(android.R.string.ok, null);

                    AlertDialog loginActivityAlert = alertBuilder.create();
                    loginActivityAlert.show();
                }
                else {
                    setProgressBarIndeterminateVisibility(true);
                    
                    ParseUser.logInInBackground(usernameText, passwordText, new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            setProgressBarIndeterminateVisibility(false);

                            if (e == null) {
                                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else {
                                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(LogInActivity.this);
                                alertBuilder.setMessage(e.getMessage());
                                alertBuilder.setTitle(R.string.oh_no_error_title);
                                alertBuilder.setPositiveButton(android.R.string.ok, null);

                                AlertDialog loginActivityAlert = alertBuilder.create();
                                loginActivityAlert.show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
