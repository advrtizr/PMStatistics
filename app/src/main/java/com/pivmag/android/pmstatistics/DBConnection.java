package com.pivmag.android.pmstatistics;

import android.os.StrictMode;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String TAG = "DBConnection";

    public Connection makeConnection(){
        StrictMode.ThreadPolicy policy = new StrictMode
                .ThreadPolicy
                .Builder()
                .permitAll()
                .build();
        StrictMode.setThreadPolicy(policy);

        Connection connection = null;

        try{
            Class.forName(Constants.CLASS_NAME);
            connection = DriverManager.getConnection(Constants.CONNECTION_URL, Constants.USER_NAME, Constants.PASSWORD);
        }catch (SQLException e){
            Log.e(TAG, "SQLException " + e.getMessage());
        }catch(ClassNotFoundException e){
            Log.e(TAG, "ClassNotFoundException " + e.getMessage());
        }catch (Exception e){
            Log.e(TAG, "Exception " + e.getMessage());
        }
        return connection;
    }
}
