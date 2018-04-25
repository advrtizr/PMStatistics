package com.pivmag.android.pmstatistics;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

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
            Log.i(TAG, "onPreExecute");
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.i(TAG, "doInBackground");
            try {
                Connection connection = mDBConnection.makeConnection();
                if (connection == null) {
                    Log.i(TAG, "Database connection error");
                    status = "Database connection error";
                } else {
                    Log.i(TAG, "Login successful");
                    String query = "SELECT * FROM sys.databases";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    ResultSetMetaData rsmd = resultSet.getMetaData();
                    int columnCount = rsmd.getColumnCount();

                    for (int i = 1; i <= columnCount; i++) {
                        StringBuilder tables = new StringBuilder();
                        String name = rsmd.getColumnName(i);
                        while (resultSet.next()) {
//                            tables.append(" | ").append(resultSet.getString(name));
                            Log.i(TAG, "Result " + resultSet.getString(name));
                        }
                    }

//                    while (resultSet.next()) {
//                        Log.i(TAG, "Result " + resultSet.getString("name"));
//                    }
                    status = "Login successful";
                    isSuccess = true;
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception " + e.getMessage());
                isSuccess = false;
                status = e.getMessage();
            }
            return status;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i(TAG, "onPostExecute");
            mProgressBar.setVisibility(View.GONE);

            if (isSuccess) {
                Intent intent = new Intent(LoginActivity.this, StatisticsActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
