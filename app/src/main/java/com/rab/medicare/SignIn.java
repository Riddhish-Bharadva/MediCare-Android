package com.rab.medicare;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SignIn extends AppCompatActivity
{
    private String notification = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            notification = bundle.getString("notification");
            DisplayToastMessage();
        }
    }

// Below code will redirect to SignUp page.
    public void SignIn(View view) throws Exception
    {
        EditText userEmail = findViewById(R.id.userEmail);
        EditText Password = findViewById(R.id.Password);
        if(userEmail.getText().toString().isEmpty())
        {
            userEmail.setError("Cannot be blank.");
            userEmail.requestFocus();
        }
        else if(Password.getText().toString().isEmpty())
        {
            Password.setError("Cannot be blank.");
            Password.requestFocus();
        }
        else
        {
            API_Call SignIn = new API_Call(userEmail.getText().toString(),Password.getText().toString());
            Thread SignInThread = new Thread(SignIn);
            SignInThread.start();
        }
    }

// Below code will redirect to SignUp page.
    public void ResetForm(View view)
    {
        EditText Email = findViewById(R.id.userEmail);
        EditText Password = findViewById(R.id.Password);
        Email.setText("");
        Password.setText("");
    }

// Below code will redirect to SignUp page.
    public void SignUp(View view)
    {
        Intent SignUpPage = new Intent(SignIn.this, SignUp.class);
        startActivity(SignUpPage);
    }

// Below code will redirect to ForgotPassword page.
    public void ForgotPassword(View view)
    {
        Intent ForgotPasswordPage = new Intent(SignIn.this, ForgotPassword.class);
        startActivity(ForgotPasswordPage);
    }

// Below onRestart will finish initially started activity and start a new activity.
    @Override
    protected void onRestart()
    {
        super.onRestart();
        this.finish();
    }

// Below code is to display message based on actions.
    public void DisplayToastMessage()
    {
        if(notification.compareTo("UserSuccessfullyRegistered") == 0)
        {
            Toast.makeText(SignIn.this,"User is successfully registered.",Toast.LENGTH_LONG).show();
        }
        else if(notification.compareTo("UserAlreadyRegistered") == 0)
        {
            Toast.makeText(SignIn.this,"User already registered. Incase you have forgot your password, click on forgot password to reset the same.",Toast.LENGTH_LONG).show();
        }
        else if(notification.compareTo("PasswordMissmatch") == 0)
        {
            Toast.makeText(SignIn.this,"Password entered is incorrect.",Toast.LENGTH_LONG).show();
        }
        else if(notification.compareTo("UserInActive") == 0)
        {
            Toast.makeText(SignIn.this,"User marked in active. Please contact admin from Contact Us page of MediCare website.",Toast.LENGTH_LONG).show();
        }
        else if(notification.compareTo("ResetLinkSent") == 0)
        {
            Toast.makeText(SignIn.this,"We have sent the password reset link to registered email id.",Toast.LENGTH_LONG).show();
        }
        else if(notification.compareTo("UserNotRegistered") == 0)
        {
            Toast.makeText(this,"Oops! Seems like the entered email id not registered with MediCare.",Toast.LENGTH_LONG).show();
        }
        else if(notification.compareTo("UserSuccessfullyDeleted") == 0)
        {
            Toast.makeText(SignIn.this,"We have successfully deleted your account at MediCare.",Toast.LENGTH_LONG).show();
        }
    }

    public class API_Call implements Runnable
    {
        private String userEmail = "";
        private String Password = "";
        private String ResponseData = "";

        public API_Call(String userEmail, String Password)
        {
            this.userEmail = userEmail;
            this.Password = Password;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void run()
        {
            String SendData = "{\"function\":\"SignIn\",\"userEmail\":\""+userEmail+"\",\"Password\":\""+Password+"\"}";
            JSONConnection JSC = new JSONConnection(SendData);
            try
            {
                ResponseData = JSC.JSONCon();
            }
            catch(Exception e)
            {
                System.out.println("Exception : "+e);
            }
            ObjectMapper OM = new ObjectMapper();
            try
            {
                API_Response API_Res = OM.readValue(ResponseData, API_Response.class);
//                System.out.println("userEmail is : "+API_Res.getUserEmail());
//                System.out.println("Notification is : "+API_Res.getNotification());
//                System.out.println("FirstName is : "+API_Res.getFirstName());
//                System.out.println("LastName is : "+API_Res.getLastName());
//                System.out.println("Contact is : "+API_Res.getContact());
//                System.out.println("Address is : "+API_Res.getAddress());
//                System.out.println("Gender is : "+API_Res.getGender());
//                System.out.println("DOB is : "+API_Res.getDOB());
//                System.out.println("EmailVerified is : "+API_Res.getEmailVerified());
                Intent GoBack;
                if(API_Res.getNotification().compareTo("PasswordMissmatch") == 0)
                {
                    GoBack = new Intent(SignIn.this, SignIn.class);
                }
                else if(API_Res.getNotification().compareTo("UserNotRegistered") == 0)
                {
                    GoBack = new Intent(SignIn.this, SignIn.class);
                }
                else
                {
                    LoggedInDecision LID = new LoggedInDecision();
                    LID.setLoginStatus(API_Res.getUserEmail());
                    GoBack = new Intent(SignIn.this, MainActivity.class);
                }
                GoBack.putExtra("notification", API_Res.getNotification());
                startActivity(GoBack);
                finish();
            }
            catch (Exception e)
            {
                System.out.println("Exception : "+e);
            }
        }
    }
}