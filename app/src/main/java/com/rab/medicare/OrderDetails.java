package com.rab.medicare;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderDetails extends AppCompatActivity {
    LoggedInDecision LID = new LoggedInDecision();
    String OrderID = null;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            OrderID = bundle.getString("OrderID");
            String userEmail = LID.getLoginStatus();
            API_Call OD;
            if(OrderID != null && userEmail != null)
            {
                TextView OrderID = findViewById(R.id.OrderID);
                OrderID.setText(this.OrderID);
                OD = new API_Call(userEmail);
                Thread ODThread = new Thread(OD);
                ODThread.start();
            }
            else
            {
                this.finish();
            }
        }
    }

// Below onRestart will finish initially started activity and start a new activity.
    @Override
    protected void onRestart()
    {
        super.onRestart();
        this.finish();
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
            String SendData = "{\"function\":\"OrderIDData\",\"userEmail\":\"" + userEmail + "\",\"OrderID\":\""+OrderID+"\"}";
            final String CurrencySymbol = "€";
            final ListView ProductList = findViewById(R.id.ProductList);
            final ArrayList<Bitmap> ProductImage = new ArrayList<Bitmap>();
            final ArrayList<String> ProductName = new ArrayList<String>();
            final ArrayList<Integer> PrescriptionRequired = new ArrayList<Integer>();
            final ArrayList<Integer> ProductQuantity = new ArrayList<Integer>();
            final ArrayList<Float> ProductPrice = new ArrayList<Float>();
            final ArrayList<String> ProductStatus = new ArrayList<String>();
            final ArrayList<String> BroughtFrom = new ArrayList<String>();
            final ArrayList<String> PharmacyAddress = new ArrayList<String>();
            final OrderDetailsCustomView[] ODCV = new OrderDetailsCustomView[1];

            JSONConnection JSC = new JSONConnection(SendData);
            try {
                ResponseData = JSC.JSONCon();
            } catch (Exception e) {
                System.out.println("Exception : " + e);
            }
//            System.out.println("Order Data : "+ResponseData);
            ObjectMapper OM = new ObjectMapper();
            try {
                final OrdersDetailsAPI API = OM.readValue(ResponseData, OrdersDetailsAPI.class);
//                System.out.println(API.getOrderType());
//                System.out.println(API.getServiceCharge());
//                System.out.println(API.getDeliveryCharge());
//                System.out.println(API.getSubTotalPrice());
                for (int i = 0; i < API.getProductID().length; i++) {
                    String ResponseData2 = null;
                    String ResponseData3 = null;

                    ProductQuantity.add(API.getQuantity()[i]);
                    ProductPrice.add(API.getPrice()[i]);
                    ProductStatus.add(API.getProductStatus()[i]);

//                    System.out.println("ProductID: "+API.getProductID()[i]);
//                    System.out.println("Price:"+API.getPrice()[i]);
//                    System.out.println("ProductStatus: "+API.getProductStatus()[i]);

                    // Below ProductData code will fetch data of each product in ProductId ArrayList.
                    String SendData2 = "{\"function\":\"ProductData\",\"userEmail\":\"" + userEmail + "\",\"ProductID\":\"" + API.getProductID()[i] + "\"}";
                    JSONConnection JSC2 = new JSONConnection(SendData2);
                    try {
                        ResponseData2 = JSC2.JSONCon();
                    } catch (Exception e) {
                        System.out.println("Exception : " + e);
                    }
                    ObjectMapper OM2 = new ObjectMapper();
                    ProductDataAPI ProductData = OM2.readValue(ResponseData2, ProductDataAPI.class);
                    ProductName.add(ProductData.getProductName());
                    PrescriptionRequired.add(Integer.parseInt(ProductData.getPrescriptionRequired()));
                    ImageURLToBitmap IUTB = new ImageURLToBitmap();
                    Bitmap ImageBitmap = IUTB.Convert(ProductData.getImage());
                    ProductImage.add(ImageBitmap);

                    // Now fetching data of pharmacy for all products in my ArrayList.
                    String SendData3 = "{\"function\":\"PharmacyData\",\"userEmail\":\"" + userEmail + "\",\"PharmacyID\":\"" + API.getPharmacyID()[i] + "\"}";
                    JSONConnection JSC3 = new JSONConnection(SendData3);
                    try {
                        ResponseData3 = JSC3.JSONCon();
                    } catch (Exception e) {
                        System.out.println("Exception : " + e);
                    }
                    ObjectMapper OM3 = new ObjectMapper();
                    PharmacyDetailsAPI PharmacyData = OM3.readValue(ResponseData3, PharmacyDetailsAPI.class);

                    BroughtFrom.add(PharmacyData.getPharmacyName());
                    PharmacyAddress.add(PharmacyData.getPhysicalAddress());

//                    System.out.println("BroughtFrom: "+PharmacyData.getPharmacyName());
//                    System.out.println("Pharmacy Address: "+PharmacyData.getPhysicalAddress());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView PR_TV = findViewById(R.id.PrescriptionRequired);
                        if(API.getPrescriptionRequired() == 1 && API.getReUploadPrescription() == 0)
                        {
                            PR_TV.setText("You have already uploaded a prescription.");
                        }
                        else if(API.getPrescriptionRequired() == 1 && API.getReUploadPrescription() == 1)
                        {
                            PR_TV.setText("There is some error with uploaded Prescription. Please re-upload a valid prescription.");
                        }
                        else
                        {
                            PR_TV.setText("There are no products in this order that requires prescription.");
                        }
                        TextView OType = findViewById(R.id.OrderType);
                        OType.setText("Order Type : "+API.getOrderType());
                        TextView SC = findViewById(R.id.ServiceCharge);
                        SC.setText("Service Charge : "+CurrencySymbol+" "+API.getServiceCharge());
                        TextView DC = findViewById(R.id.DeliveryCharge);
                        DC.setText("Delivery Charge : "+CurrencySymbol+" "+API.getDeliveryCharge());
                        TextView OTotal = findViewById(R.id.OrderTotal);
                        float Total = API.getSubTotalPrice()+API.getServiceCharge()+API.getDeliveryCharge();
                        DecimalFormat DF = new DecimalFormat("0.00");
                        DF.setRoundingMode(RoundingMode.UP);
                        OTotal.setText("Order Total : "+CurrencySymbol+" "+DF.format(Total));
                        ODCV[0] = new OrderDetailsCustomView(OrderDetails.this, ProductImage, ProductName, PrescriptionRequired, ProductQuantity, ProductPrice, ProductStatus, BroughtFrom, PharmacyAddress);
                        ProductList.setAdapter(ODCV[0]);
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