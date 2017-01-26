package com.example.malmike21.orahiapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malmike21.orahiapp.POJO.GeneralResponse;
import com.example.malmike21.orahiapp.POJO.User;
import com.example.malmike21.orahiapp.R;
import com.example.malmike21.orahiapp.Rest.ErrorUtils;
import com.example.malmike21.orahiapp.Rest.RetrofitBuilder;
import com.example.malmike21.orahiapp.constants.Constants;
import com.example.malmike21.orahiapp.sessionManager.SharedInformation;
import com.example.malmike21.orahiapp.sessionManager.UserSessionManager;
import com.example.malmike21.orahiapp.Rest.interfaces.RequestInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    //private static final int REQUEST_SIGNUP = 0;

    EditText _emailText;
    TextView _anyThing;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;
    UserSessionManager session;
    SharedInformation  information = SharedInformation.getInstance();
    //private SharedPreferences pref;
    User user;
    boolean proofUserName = false;
    RetrofitBuilder retrofitBuilder = new RetrofitBuilder();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);
        _signupLink = (TextView) findViewById(R.id.link_signup);
        /*_anyThing = (TextView) findViewById(R.id.anyThing);*/
        session = new UserSessionManager(getApplicationContext());


        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String _userName = null;
        String _email = null;

        if(this.proofUserName){
            _userName = _emailText.getText().toString();
        }else{
            _email = _emailText.getText().toString();
        }

        final String userName = _userName;
        final String email = _email;
        final String password = _passwordText.getText().toString();


        user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUserName(userName);
        //authentication.userAuthentication(user);

        RequestInterface requestInterface = retrofitBuilder.getRetrofitlogin().create(RequestInterface.class);
        Call<GeneralResponse> response = requestInterface.login(user);


        response.enqueue(new Callback<GeneralResponse> () {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {

                if(response.isSuccessful()){
                    GeneralResponse resp = response.body();
                    session.createUserLoginSession(userName,email,password,resp.getToken());
                    information.setUser(user);
                    information.setGeneralResponse(resp);

                    Toast.makeText(getApplicationContext(),"Login success",Toast.LENGTH_LONG).show();

                    onLoginSuccess();
                    progressDialog.dismiss();
                }
                else{
                    String message;
                    GeneralResponse resp = ErrorUtils.parseError(retrofitBuilder, response);


                    Log.d(Constants.TAG, response.errorBody().toString());
                    message = resp.getMessage();
                    /*if(resp.getMessage() != null){  message = resp.getMessage();}
                    else{ message = "Login Failed";}*/
                    /*_anyThing.setText(response.errorBody().toString());*/
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    _loginButton.setEnabled(true);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                Log.d(Constants.TAG,"failed");
                String message = "Login Failed. Check your internet connection";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                _loginButton.setEnabled(true);
                progressDialog.dismiss();
            }
        });

    }




    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(false);
    }

    public void onLoginSuccess() {
        Toast.makeText(getApplicationContext(),"Login success",Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(false);
        this.setResult(RESULT_OK, null);
        Intent intent = new Intent(getApplicationContext(), GridCategoryActivity.class);
        startActivity(intent);
        this.finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), information.getGeneralResponse().getMessage(), Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() ) {
            _emailText.setError("enter a valid email address");
            valid = false;
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            _emailText.setError(null);
            this.proofUserName = true;
        }else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
