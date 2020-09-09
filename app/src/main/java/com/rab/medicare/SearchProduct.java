package com.rab.medicare;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;

public class SearchProduct extends AppCompatActivity
{
    String SearchKeyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            SearchKeyword = bundle.getString("SearchKeyword");
            API_Call HP = new API_Call();
            Thread SearchPageThread = new Thread(HP);
            SearchPageThread.start();
        }
    }
    public class API_Call implements Runnable
    {
        private String ResponseData = "";

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void run()
        {
            String SendData = "{\"function\":\"SearchProduct\",\"userEmail\":\"\",\"SearchKeyword\":\""+SearchKeyword+"\"}";
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
                if(P_API.getProductID().length > 0)
                {
                    for (int i = 0; i < P_API.getProductID().length; i++) {
                        String ResponseData2 = null;
                        String SendData2 = "{\"function\":\"ProductData\",\"userEmail\":\"\",\"ProductID\":\"" + P_API.getProductID()[i] + "\"}";
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
                            MA[0] = new HomePageCustomView(SearchProduct.this, ProductID, ProductName, ProductPrice, PrescriptionRequired, ProductImage, SearchProduct.this);
                            ProductList.setAdapter(MA[0]);
                        }
                    });
                }
                else
                {
                    TextView MessageTV = findViewById(R.id.MessageTV);
                    MessageTV.setText("We really tried our best to find the product you are looking for but unfortunately there are no results for your search keyword.");
                }
            }
            catch (Exception e)
            {
                System.out.println("Exception : " + e);
            }
        }
    }
}