package com.rab.medicare;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String notification = null;
    public static String WebLink = "https://medicare-287205.nw.r.appspot.com/API_MediCare";
    LoggedInDecision LID = new LoggedInDecision();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button ProfileButton = findViewById(R.id.Profile);
        Button CartButton = findViewById(R.id.Cart);
        Button MyOrdersButton = findViewById(R.id.MyOrders);
        Button SignInButton = findViewById(R.id.SignIn);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            notification = bundle.getString("notification");
            if (notification != null) {
                DisplayToastMessage();
            }
        }
        String userEmail = LID.getLoginStatus();
        API_Call HP;
        if(userEmail == null)
        {
            SignInButton.setVisibility(View.VISIBLE);
            ProfileButton.setVisibility(View.INVISIBLE);
            CartButton.setVisibility(View.INVISIBLE);
            MyOrdersButton.setVisibility(View.INVISIBLE);
            HP = new API_Call(""); // Email address is sent blank.
        }
        else
        {
            SignInButton.setVisibility(View.INVISIBLE);
            ProfileButton.setVisibility(View.VISIBLE);
            CartButton.setVisibility(View.VISIBLE);
            MyOrdersButton.setVisibility(View.VISIBLE);
            HP = new API_Call(userEmail); // If loggedIn, userEmail will be sent.
        }
        Thread HomePageThread = new Thread(HP);
        HomePageThread.start();
    }

// Below code handles page redirection to SignIn Page and SignOut functionality.
    public void SignIn(View view)
    {
        LID.setLoginStatus(null);
        Intent SignIn = new Intent(this, SignIn.class);
        startActivity(SignIn);
    }

    public void Profile(View view)
    {
        if(LID.getLoginStatus() != null)
        {
            Intent Profile = new Intent(this, Profile.class);
            startActivity(Profile);
        }
    }

    public void Cart(View view)
    {
        if(LID.getLoginStatus() != null)
        {
            Intent Cart = new Intent(this, MainActivity.class);
            startActivity(Cart);
        }
    }

    public void MyOrders(View view)
    {
        if(LID.getLoginStatus() != null)
        {
            Intent MyOrders = new Intent(this, MyOrders.class);
            startActivity(MyOrders);
        }
    }

    public void SearchProduct(View view)
    {
        EditText SearchBarET = findViewById(R.id.SearchBar);
        if(SearchBarET.getText().toString().compareTo("") != 0)
        {
            Intent SearchProductPage = new Intent(this, SearchProduct.class);
            SearchProductPage.putExtra("SearchKeyword", SearchBarET.getText().toString());
            startActivity(SearchProductPage);
        }
        else
        {
            SearchBarET.setError("Cannot be blank.");
            SearchBarET.requestFocus();
        }
    }

// Below onRestart will finish initially started activity and start a new activity.
    @Override
    protected void onRestart()
    {
        super.onRestart();
        this.finish();
        Intent RefreshPage = new Intent(this, MainActivity.class);
        startActivity(RefreshPage);
    }

// Below code is to display message based on actions.
    public void DisplayToastMessage() {
        if (notification.compareTo("SuccessfulSignIn") == 0)
        {
            Toast.makeText(this, "Successfully signed in.", Toast.LENGTH_LONG).show();
        }
        else if (notification.compareTo("SuccessfulSignOut") == 0)
        {
            Toast.makeText(this, "Successfully signed out.", Toast.LENGTH_LONG).show();
        }
        else if (notification.compareTo("ProfileSuccessfullyUpdated") == 0)
        {
            Toast.makeText(this, "Your profile details are successfully updated.", Toast.LENGTH_LONG).show();
        }
        else if (notification.compareTo("UserNotRegistered") == 0)
        {
            Toast.makeText(this, "Email address is not registered.", Toast.LENGTH_LONG).show();
        }
    }

    public class API_Call implements Runnable
    {
        private String userEmail = "";
        private String ResponseData = "";

        public API_Call(String userEmail)
        {
            this.userEmail = userEmail;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void run()
        {
            String SendData = "{\"function\":\"AllProductID\",\"userEmail\":\"" + userEmail + "\"}";
            JSONConnection JSC = new JSONConnection(SendData);
            final ListView ProductList = findViewById(R.id.ProductList);
            final ArrayList<String> ProductID = new ArrayList<String>();
            final ArrayList<String> ProductName = new ArrayList<String>();
            final ArrayList<String> ProductPrice = new ArrayList<String>();
            final ArrayList<String> PrescriptionRequired = new ArrayList<String>();
            final ArrayList<Bitmap> ProductImage = new ArrayList<Bitmap>();
            final HomePageCustomView[] MA = new HomePageCustomView[1];
            try {
                ResponseData = JSC.JSONCon();
            } catch (Exception e) {
                System.out.println("Exception : " + e);
            }
            ObjectMapper OM = new ObjectMapper();
            try {
                ProductsAPI P_API = OM.readValue(ResponseData, ProductsAPI.class);
                for (int i = 0; i < P_API.getProductID().length; i++) {
                    String ResponseData2 = null;
                    String SendData2 = "{\"function\":\"ProductData\",\"userEmail\":\"" + userEmail + "\",\"ProductID\":\"" + P_API.getProductID()[i] + "\"}";
                    JSONConnection JSC2 = new JSONConnection(SendData2);
                    try {
                        ResponseData2 = JSC2.JSONCon();
                    } catch (Exception e) {
                        System.out.println("Exception : " + e);
                    }
                    ObjectMapper OM2 = new ObjectMapper();
// Below ProductData code will fetch data of each product in ProductId ArrayList.
                    ProductDataAPI ProductData_API = OM2.readValue(ResponseData2, ProductDataAPI.class);
                    ProductID.add(P_API.getProductID()[i]);
                    ProductName.add(ProductData_API.getProductName());
                    ProductPrice.add(ProductData_API.getPrice());
                    PrescriptionRequired.add(ProductData_API.getPrescriptionRequired());
                    ImageURLToBitmap IUTB = new ImageURLToBitmap();
                    final Bitmap ImageBitmap = IUTB.Convert(ProductData_API.getImage());
                    ProductImage.add(ImageBitmap);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MA[0] = new HomePageCustomView(MainActivity.this, ProductID, ProductName, ProductPrice, PrescriptionRequired, ProductImage, MainActivity.this);
                        ProductList.setAdapter(MA[0]);
                    }
                });
            }
            catch (Exception e)
            {
                System.out.println("Exception : " + e);
            }
        }
    }
}