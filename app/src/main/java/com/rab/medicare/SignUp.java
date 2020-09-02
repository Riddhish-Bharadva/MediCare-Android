package com.rab.medicare;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUp extends AppCompatActivity
{
    private EditText FirstName;
    private EditText LastName;
    private EditText SignUpEmailId;
    private EditText SignUpPassword;
    private EditText SignUpContact;
    private EditText SignUpPostalAddress;
    private RadioGroup GenderGroup;
    private RadioButton Gender;
    private EditText DOB;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirstName = findViewById(R.id.FirstName);
        LastName = findViewById(R.id.LastName);
        SignUpEmailId = findViewById(R.id.SignUpEmailId);
        SignUpPassword = findViewById(R.id.SignUpPassword);
        SignUpContact = findViewById(R.id.SignUpContact);
        SignUpPostalAddress = findViewById(R.id.SignUpPostalAddress);
        GenderGroup = findViewById(R.id.Gender);
        DOB = findViewById(R.id.DateOfBirth);

        final Calendar myCalendar = Calendar.getInstance();
        final EditText DateOfBirth = findViewById(R.id.DateOfBirth);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                DateOfBirth.setText(sdf.format(myCalendar.getTime()));
            }
        };
        DateOfBirth.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new DatePickerDialog(SignUp.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
// Below onRestart will finish initially started activity and start a new activity.
    @Override
    protected void onRestart()
    {
        super.onRestart();
        this.finish();
    }
    public void SignUp(View view) throws Exception
    {
        int CheckAllFields = 1;
        int G = GenderGroup.getCheckedRadioButtonId();
        Gender = findViewById(G);

        if(FirstName.getText().toString().isEmpty())
        {
            FirstName.setError("Cannot be blank.");
            FirstName.requestFocus();
            CheckAllFields = 0;
        }
        else
        {
            System.out.println(FirstName.getText());
        }
        if(LastName.getText().toString().isEmpty())
        {
            LastName.setError("Cannot be blank.");
            LastName.requestFocus();
            CheckAllFields = 0;
        }
        else
        {
            System.out.println(LastName.getText());
        }
        if(SignUpEmailId.getText().toString().isEmpty())
        {
            SignUpEmailId.setError("Cannot be blank.");
            SignUpEmailId.requestFocus();
            CheckAllFields = 0;
        }
        else
        {
            System.out.println(SignUpEmailId.getText());
        }
        if(SignUpPassword.getText().toString().isEmpty())
        {
            SignUpPassword.setError("Cannot be blank.");
            SignUpPassword.requestFocus();
            CheckAllFields = 0;
        }
        else
        {
            System.out.println(SignUpPassword.getText());
        }
        if(SignUpContact.getText().toString().isEmpty())
        {
            SignUpContact.setError("Cannot be blank.");
            SignUpContact.requestFocus();
            CheckAllFields = 0;
        }
        else
        {
            System.out.println(SignUpContact.getText());
        }
        if(SignUpPostalAddress.getText().toString().isEmpty())
        {
            SignUpPostalAddress.setError("Cannot be blank.");
            SignUpPostalAddress.requestFocus();
            CheckAllFields = 0;
        }
        else
        {
            System.out.println(SignUpPostalAddress.getText());
        }
        if(GenderGroup.getCheckedRadioButtonId() == -1)
        {
            CheckAllFields = 0;
        }
        else
        {
            System.out.println(Gender.getText());
        }
        if(DOB.getText().toString().isEmpty())
        {
            DOB.setError("Cannot be blank.");
            DOB.requestFocus();
            CheckAllFields = 0;
        }
        else
        {
            System.out.println(DOB.getText());
        }
        System.out.println("Check All Fields : "+CheckAllFields);
        if(CheckAllFields == 1)
        {
            API_Call SignUp = new API_Call(FirstName.getText().toString(),LastName.getText().toString(),SignUpEmailId.getText().toString(),SignUpPassword.getText().toString(),SignUpContact.getText().toString(),SignUpPostalAddress.getText().toString(),Gender.getText().toString(),DOB.getText().toString());
            System.out.println("Connecting to API.");
            Thread SignUpThread = new Thread(SignUp);
            SignUpThread.start();
        }
    }
    public void ResetForm(View view)
    {
        FirstName.setText("");
        LastName.setText("");
        SignUpEmailId.setText("");
        SignUpPassword.setText("");
        SignUpContact.setText("");
        SignUpPostalAddress.setText("");
        int G = GenderGroup.getCheckedRadioButtonId();
        Gender = findViewById(G);
        Gender.setChecked(false);
        DOB.setText("");
    }

    public class API_Call implements Runnable
    {
        private String ResponseData = "";
        private String FirstName, LastName, userEmail, Password, Contact, Address, Gender, DOB;

        public API_Call(String FirstName, String LastName, String userEmail, String Password, String Contact, String Address, String Gender, String DOB)
        {
            this.FirstName = FirstName;
            this.LastName = LastName;
            this.userEmail = userEmail;
            this.Password = Password;
            this.Contact = Contact;
            this.Address = Address;
            this.Gender = Gender;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                this.DOB = sdf.format(inputFormat.parse(DOB));
            }
            catch(Exception e)
            {
                System.out.println("Exception while converting date : "+e);
                e.printStackTrace();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void run()
        {
            String SendData = "{\"function\":\"SignUp\",\"userEmail\":\""+userEmail+"\",\"FirstName\":\""+FirstName+"\",\"LastName\":\""+LastName+"\",\"Password\":\""+Password+"\",\"Contact\":\""+Contact+"\",\"Address\":\""+Address+"\",\"Gender\":\""+Gender+"\",\"DOB\":\""+this.DOB+"\"}";
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
            Intent GoBack = new Intent(SignUp.this, SignIn.class);
            GoBack.putExtra("notification", API_Res.getNotification());
            startActivity(GoBack);
        }
    }
}