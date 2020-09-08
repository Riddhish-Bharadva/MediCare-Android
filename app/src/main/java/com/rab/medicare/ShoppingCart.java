package com.rab.medicare;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;

public class ShoppingCart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        LoggedInDecision LID = new LoggedInDecision();
        String userEmail = LID.getLoginStatus();
        API_Call SC;
        if(userEmail == null)
        {
            Intent GoBack = new Intent(ShoppingCart.this, MainActivity.class);
            startActivity(GoBack);
        }
        else
        {
            SC = new API_Call(userEmail); // If loggedIn, userEmail will be sent.
            Thread ShoppingCartThread = new Thread(SC);
            ShoppingCartThread.start();
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
            String SendData = "{\"function\":\"FetchCartData\",\"userEmail\":\"" + userEmail + "\"}";
            JSONConnection JSC = new JSONConnection(SendData);
            final ListView ProductList = findViewById(R.id.ProductList);
            final ArrayList<String> ProductID = new ArrayList<String>();
            final ArrayList<String> ProductName = new ArrayList<String>();
            final ArrayList<String> ProductPrice = new ArrayList<String>();
            final ArrayList<String> PrescriptionRequired = new ArrayList<String>();
            final ArrayList<Bitmap> ProductImage = new ArrayList<Bitmap>();
            final ShoppingCartCustomView[] SC = new ShoppingCartCustomView[1];
            try {
                ResponseData = JSC.JSONCon();
            } catch (Exception e) {
                System.out.println("Exception : " + e);
            }
            System.out.println(ResponseData);
            ObjectMapper OM = new ObjectMapper();
            try {
                ProductsAPI P_API = OM.readValue(ResponseData, ProductsAPI.class);
                final String notification = P_API.getNotification();
                if(notification.compareTo("NoProductsInCart") != 0)
                {
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
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(notification.compareTo("NoProductsInCart") == 0)
                        {
                            TextView CM = findViewById(R.id.CartMessage);
                            CM.setText("There are no products in your cart to display here.");
                        }
                        else
                        {
                            SC[0] = new ShoppingCartCustomView(ShoppingCart.this, ProductID, ProductName, ProductPrice, PrescriptionRequired, ProductImage);
                            ProductList.setAdapter(SC[0]);
                        }
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