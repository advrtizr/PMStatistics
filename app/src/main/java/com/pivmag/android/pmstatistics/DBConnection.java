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
        String connectionURL = null;
        try{
            Class.forName(Constants.CLASS_NAME);
            connectionURL = Constants.CONNECTION_URL +
                    Constants.IP_ADDRESS +
                    ";databasename=" + Constants.DATABASE_NAME +
                    ";user=" + Constants.USER_NAME +
                    ";password=" + Constants.PASSWORD + ";";
            connection = DriverManager.getConnection(connectionURL);
        }catch (SQLException e){
            Log.e(TAG, e.getMessage());
        }catch(ClassNotFoundException e){
            Log.e(TAG, e.getMessage());
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        return connection;
    }
}
