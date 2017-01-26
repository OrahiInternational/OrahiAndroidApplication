package com.example.malmike21.orahiapp.activities;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.malmike21.orahiapp.POJO.GeneralResponse;
import com.example.malmike21.orahiapp.POJO.User;
import com.example.malmike21.orahiapp.R;
import com.example.malmike21.orahiapp.sessionManager.SharedInformation;
import com.example.malmike21.orahiapp.sessionManager.UserSessionManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new UserSessionManager(getApplicationContext());

        if(session.isUserLoggedIn()){
            HashMap<String, String> userDetails = session.getUserDetails();
            User user = new User();
            user.setUserName(userDetails.get("UserName"));
            user.setEmail(userDetails.get("email"));
            user.setPassword(userDetails.get("password"));

            String token = userDetails.get("token");
            SharedInformation.getInstance().setUser(user);
            GeneralResponse generalResponse = new GeneralResponse();
            generalResponse.setToken(token);
            SharedInformation.getInstance().setGeneralResponse(generalResponse);

            Intent intent= new Intent(this, GridCategoryActivity.class);
            startActivity(intent);
            finish();

        }
        else
        {

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

        }

    }
}
