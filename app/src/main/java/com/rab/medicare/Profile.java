package com.rab.medicare;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Profile extends AppCompatActivity
{
    LoggedInDecision LID = new LoggedInDecision();
    ObjectMapper OM = new ObjectMapper();
    API_Response API_Res = new API_Response();
    EditText FirstNameET, LastNameET, ContactET, AddressET;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirstNameET = findViewById(R.id.FirstName);
        LastNameET = findViewById(R.id.LastName);
        ContactET = findViewById(R.id.Contact);
        AddressET = findViewById(R.id.Address);
        if(LID.getLoginStatus() != null)
        {
            API_Call Profile = new API_Call("FetchProfileData", LID.getLoginStatus());
            Thread ProfileThread = new Thread(Profile);
            ProfileThread.start();
        }
        else
        {
            Intent Redirect = new Intent(Profile.this, SignIn.class);
            startActivity(Redirect);
        }
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        this.finish();
    }

// Below code handles page redirection to SignIn Page and SignOut functionality.
    public void SignOut(View view)
    {
        if(LID.getLoginStatus() != null)
        {
            LID.setLoginStatus(null);
            Intent SignOut = new Intent(this, MainActivity.class);
            SignOut.putExtra("notification","SuccessfulSignOut");
            startActivity(SignOut);
        }
        else
        {
            Intent SignIn = new Intent(this, SignIn.class);
            startActivity(SignIn);
        }
    }

    public void UpdateButton(View view)
    {
//        System.out.println("Login Status : "+LID.getLoginStatus());
        if(LID.getLoginStatus() != null)
        {
            if(FirstNameET.getText().toString().compareTo("") == 0)
            {
                FirstNameET.setError("Cannot be blank.");
                FirstNameET.requestFocus();
            }
            else if(LastNameET.getText().toString().compareTo("") == 0)
            {
                LastNameET.setError("Cannot be blank.");
                LastNameET.requestFocus();
            }
            else if(ContactET.getText().toString().compareTo("") == 0)
            {
                ContactET.setError("Cannot be blank.");
                ContactET.requestFocus();
            }
            else if(AddressET.getText().toString().compareTo("") == 0)
            {
                AddressET.setError("Cannot be blank.");
                AddressET.requestFocus();
            }
            else
            {
                API_Call UpdateProfile = new API_Call("UpdateProfileData", LID.getLoginStatus());
                Thread UpdateProfileThread = new Thread(UpdateProfile);
                UpdateProfileThread.start();
            }
        }
        else
        {
            Intent Redirect = new Intent(Profile.this, SignIn.class);
            startActivity(Redirect);
        }
    }

    public void ResetButton(View view)
    {
        FirstNameET.setText(API_Res.getFirstName());
        LastNameET.setText(API_Res.getLastName());
        ContactET.setText(API_Res.getContact());
        AddressET.setText(API_Res.getAddress());
    }

    public class API_Call implements Runnable
    {
        private String userEmail = "";
        private String Function = "";
        private String ResponseData = "";
        private String SendData = null;

        public API_Call(String Function, String userEmail)
        {
            this.Function = Function;
            this.userEmail = userEmail;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void run()
        {
            if(Function.compareTo("FetchProfileData") == 0) // This is to fetch user data.
            {
                SendData = "{\"function\":\""+Function+"\",\"userEmail\":\""+userEmail+"\"}";
            }
            else // This is for updating user data.
            {
                String FN = FirstNameET.getText().toString();
                String LN = LastNameET.getText().toString();
                String C = ContactET.getText().toString();
                String A = AddressET.getText().toString();
                SendData = "{\"function\":\""+Function+"\",\"userEmail\":\""+userEmail+"\",\"FirstName\":\""+FN+"\",\"LastName\":\""+LN+"\",\"Contact\":\""+C+"\",\"Address\":\""+A+"\"}";
            }
            if(SendData != null)
            {
                JSONConnection JSC = new JSONConnection(SendData);
                try
                {
                    ResponseData = JSC.JSONCon();
                    API_Res = OM.readValue(ResponseData, API_Response.class);

                    if(Function.compareTo("FetchProfileData") == 0) // This is to display fetched user data.
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView userEmailTV = findViewById(R.id.userEmail);
                                TextView GenderTV = findViewById(R.id.Gender);
                                TextView DOBTV = findViewById(R.id.DOB);
                                userEmailTV.setText("Email : "+API_Res.getUserEmail());
                                FirstNameET.setText(API_Res.getFirstName());
                                LastNameET.setText(API_Res.getLastName());
                                ContactET.setText(API_Res.getContact());
                                AddressET.setText(API_Res.getAddress());
                                GenderTV.setText("Gender : "+API_Res.getGender());
                                DOBTV.setText("Date of Birth : "+API_Res.getDOB());
                                    }
                        });
                    }
                    else
                    {
//                        System.out.println("userEmail is : "+API_Res.getUserEmail());
//                        System.out.println("Notification is : "+API_Res.getNotification());
//                        System.out.println("FirstName is : "+API_Res.getFirstName());
//                        System.out.println("LastName is : "+API_Res.getLastName());
//                        System.out.println("Contact is : "+API_Res.getContact());
//                        System.out.println("Address is : "+API_Res.getAddress());
//                        System.out.println("Gender is : "+API_Res.getGender());
//                        System.out.println("DOB is : "+API_Res.getDOB());
//                        System.out.println("EmailVerified is : "+API_Res.getEmailVerified());
                        Intent GoBack = new Intent(Profile.this, MainActivity.class);
                        GoBack.putExtra("notification", API_Res.getNotification());
                        startActivity(GoBack);
                    }
                }
                catch(Exception e)
                {
                    System.out.println("Exception : "+e);
                }
            }
        }
    }
}