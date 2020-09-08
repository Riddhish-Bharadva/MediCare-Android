package com.rab.medicare;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductDetails extends AppCompatActivity
{
    String ProductID = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ProductID = bundle.getString("ProductID");
            if (ProductID != null)
            {
                System.out.println(ProductID);
                API_Call HP = new API_Call(ProductID);
                Thread HomePageThread = new Thread(HP);
                HomePageThread.start();
            }
        }
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        this.finish();
    }

    public void AddToCart(View view)
    {
        LoggedInDecision LID = new LoggedInDecision();
        Intent AddToCartFunction;
        if(LID.getLoginStatus() == null)
        {
            AddToCartFunction = new Intent(this, SignIn.class);
            startActivity(AddToCartFunction);
        }
        else
        {
            if(ProductID != null)
            {
                AddToCart ATC = new AddToCart();
                ATC.AddProduct(ProductID);
                try
                {
                    Thread.sleep(250);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                API_Response API_Res = new API_Response();
                String Status = API_Res.getNotification();
                System.out.println(API_Res.getNotification());
                if(Status.compareTo("ProductSuccessfullyAdded") == 0)
                {
                    Toast.makeText(this,"Product successfully added to your cart.",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(this,"Failed to add product to your cart. Please check your internet connection and try again.",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public class API_Call implements Runnable
    {
        private String ProductID = null;
        private String ResponseData = "";

        public API_Call(String ProductID)
        {
            this.ProductID = ProductID;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void run()
        {
            String SendData = "{\"function\":\"ProductData\",\"userEmail\":\"\",\"ProductID\":\"" + ProductID + "\"}";
            JSONConnection JSC = new JSONConnection(SendData);
            try
            {
                ResponseData = JSC.JSONCon();
                ObjectMapper OM = new ObjectMapper();
                final ProductDataAPI P_API = OM.readValue(ResponseData, ProductDataAPI.class);
                ImageURLToBitmap IUTB = new ImageURLToBitmap();
                final Bitmap ImageBitmap = IUTB.Convert(P_API.getImage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        ImageView ProductImage = findViewById(R.id.ProductImage);
                        ProductImage.setImageBitmap(ImageBitmap);
                        TextView ProductName = findViewById(R.id.ProductName);
                        ProductName.setText("Product Name : "+P_API.getProductName());
                        TextView AvailableQuantity = findViewById(R.id.AvailableQuantity);
                        AvailableQuantity.setText("Available Quantity : "+P_API.getQuantity());
                        TextView PrescriptionRequired = findViewById(R.id.PrescriptionRequired);
                        if(P_API.getPrescriptionRequired().compareTo("1") == 0){
                            PrescriptionRequired.setText("Prescription Required : You will need a prescription to buy this product.");
                        }
                        else
                        {
                            PrescriptionRequired.setText("Prescription Required : Prescription is not required to buy this product.");
                        }
                        TextView Description = findViewById(R.id.Description);
                        Description.setText("Description : \n"+P_API.getDescription());
                        TextView ProductPrice = findViewById(R.id.ProductPrice);
                        ProductPrice.setText("ProductPrice : "+P_API.getPrice());
                        TextView Ingredients = findViewById(R.id.Ingredients);
                        if(P_API.getIngredients().compareTo("") == 0)
                        {
                            Ingredients.setText("Ingredients : Not applicable.");
                        }
                        else
                        {
                            Ingredients.setText("Ingredients : \n"+P_API.getIngredients());
                        }
                        TextView Dosage = findViewById(R.id.Dosage);
                        if(P_API.getDosage().compareTo("") == 0)
                        {
                            Dosage.setText("Dosage : Not applicable");
                        }
                        else
                        {
                            Dosage.setText("Dosage : "+P_API.getDosage());
                        }
                        TextView ProductLife = findViewById(R.id.ProductLife);
                        if(P_API.getProductLife().compareTo("") == 0)
                        {
                            ProductLife.setText("Product Life : Not applicable");
                        }
                        else
                        {
                            ProductLife.setText("Product Life : "+P_API.getProductLife());
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