package com.pivmag.android.pmstatistics;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.sql.Connection;

public class LoginActivity extends AppCompatActivity {

    private DBConnection mDBConnection;
    private ProgressBar mProgressBar;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDBConnection = new DBConnection();
        mLoginButton = findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginTask loginTask = new LoginTask();
                loginTask.execute("");
            }
        });
        mProgressBar = findViewById(R.id.login_progressbar);
        mProgressBar.setVisibility(View.GONE);
    }

    private class LoginTask extends AsyncTask<String, String, String> {

        private String status = "";
        boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                Connection connection = mDBConnection.makeConnection();
                if(connection == null){
                    status = "Database connection error";
                }else{
                    status = "Login successful";
                    isSuccess = true;
                }
            }catch (Exception e){
                isSuccess = false;
                status = e.getMessage();
            }
            return status;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mProgressBar.setVisibility(View.GONE);

            if(isSuccess){
                Intent intent = new Intent(LoginActivity.this, StatisticsActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
