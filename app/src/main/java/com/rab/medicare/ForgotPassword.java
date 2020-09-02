package com.rab.medicare;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ForgotPassword extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }
    public void ForgotPassword(View view)
    {
        EditText FPEmailID = findViewById(R.id.FPEmailId);
        String FPEmailId = FPEmailID.getText().toString();
        if(FPEmailId.isEmpty())
        {
            FPEmailID.setError("Email cannot be blank.");
            FPEmailID.requestFocus();
        }
        else
        {
            API_Call FP = new API_Call(FPEmailId);
            Thread ForgotPasswordThread = new Thread(FP);
            ForgotPasswordThread.start();
        }
    }
    public void ResetForm(View view)
    {
        EditText ForgetPasswordEmailId = findViewById(R.id.FPEmailId);
        ForgetPasswordEmailId.setText("");
    }
// Below onRestart will finish initially started activity and restart ForgotPassword.
    @Override
    protected void onRestart()
    {
        super.onRestart();
        this.finish();
    }

    public class API_Call implements Runnable
    {
        private String FPEmailId = "";
        private String ResponseData = "";

        public API_Call(String FPEmailId)
        {
            this.FPEmailId = FPEmailId;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void run()
        {
            String SendData = "{\"function\":\"ForgotPassword\",\"userEmail\":\"" + FPEmailId + "\"}";
            JSONConnection JSC = new JSONConnection(SendData);
            try
            {
                ResponseData = JSC.JSONCon();
            }
            catch(Exception e)
            {
                System.out.println("Exception1 : "+e);
            }
            API_Response API_Res = new API_Response();
            ObjectMapper OM = new ObjectMapper();
            try
            {
                API_Res = OM.readValue(ResponseData, API_Response.class);
            }
            catch (Exception e)
            {
                System.out.println("Exception4 : "+e);
            }
            System.out.println("Notification is : "+API_Res.getNotification());
            Intent GoBack = new Intent(ForgotPassword.this, SignIn.class);
            GoBack.putExtra("notification", API_Res.getNotification());
            startActivity(GoBack);
        }
    }
}