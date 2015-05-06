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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUpActivity extends ActionBarActivity {

    protected EditText username;
    protected EditText password;
    protected EditText email;
    protected Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_sign_up);

        username = (EditText)findViewById(R.id.usernameField);
        password = (EditText)findViewById(R.id.passwordField);
        email = (EditText)findViewById(R.id.emailField);
        signUpButton = (Button)findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();
                String emailText = email.getText().toString();

                usernameText = usernameText.trim();
                passwordText = passwordText.trim();
                emailText = emailText.trim();

                if(usernameText.isEmpty() || passwordText.isEmpty() || emailText.isEmpty()) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SignUpActivity.this);
                    alertBuilder.setMessage(R.string.sign_up_error_message);
                    alertBuilder.setTitle(R.string.oh_no_error_title);
                    alertBuilder.setPositiveButton(android.R.string.ok, null);

                    AlertDialog signUpActivityAlert = alertBuilder.create();
                    signUpActivityAlert.show();
                }
                else {
                    setProgressBarIndeterminateVisibility(true);

                    ParseUser newUser = new ParseUser();
                    newUser.setUsername(usernameText);
                    newUser.setPassword(passwordText);
                    newUser.setEmail(emailText);

                    newUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            setProgressBarIndeterminateVisibility(false);

                            if (e == null) {
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else {
                                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SignUpActivity.this);
                                alertBuilder.setMessage(e.getMessage());
                                alertBuilder.setTitle(R.string.oh_no_error_title);
                                alertBuilder.setPositiveButton(android.R.string.ok, null);

                                AlertDialog signUpActivityAlert = alertBuilder.create();
                                signUpActivityAlert.show();
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
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
