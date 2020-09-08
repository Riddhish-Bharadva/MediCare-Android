package com.rab.medicare;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyOrders extends AppCompatActivity {

    LoggedInDecision LID = new LoggedInDecision();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        String userEmail = LID.getLoginStatus();
        API_Call MyOrdersPage = new API_Call();
        if(userEmail == null)
        {
            Intent SignInPage = new Intent(this, SignIn.class);
            startActivity(SignInPage);
        }
        else
        {
            MyOrdersPage = new API_Call(userEmail); // If loggedIn, userEmail will be sent.
        }
        Thread HomePageThread = new Thread(MyOrdersPage);
        HomePageThread.start();
    }
// Below onRestart will finish this activity.
    @Override
    protected void onRestart()
    {
        super.onRestart();
        this.finish();
        Intent Refresh = new Intent(this,MyOrders.class);
        startActivity(Refresh);
    }

    public class API_Call implements Runnable
    {
        private String userEmail = "";
        private String ResponseData = "";

        public API_Call(){}

        public API_Call(String userEmail)
        {
            this.userEmail = userEmail;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void run()
        {
            String SendData = "{\"function\":\"MyOrdersData\",\"userEmail\":\"" + userEmail + "\"}";
            JSONConnection JSC = new JSONConnection(SendData);
            final ListView MyOrdersList = findViewById(R.id.MyOrdersList);
            final ArrayList<String> OrderID = new ArrayList<String>();
            final ArrayList<String> OrderType = new ArrayList<String>();
            final ArrayList<String> OrderStatus = new ArrayList<String>();
            final ArrayList<String> OrderTotal = new ArrayList<String>();
            final MyOrdersCustomView[] MA = new MyOrdersCustomView[1];
            try
            {
                ResponseData = JSC.JSONCon();
            }
            catch (Exception e)
            {
                System.out.println("Exception : " + e);
            }
            ObjectMapper OM = new ObjectMapper();
            try {
                OrdersAPI O_API = OM.readValue(ResponseData, OrdersAPI.class);
                DecimalFormat DF = new DecimalFormat("0.00");
                DF.setRoundingMode(RoundingMode.UP);
                for (int i = 0; i < O_API.getOrderID().length; i++) {
                    OrderID.add(O_API.getOrderID()[i]);
                    OrderType.add(O_API.getOrderType()[i]);
                    OrderStatus.add(O_API.getOrderStatus()[i]);
                    OrderTotal.add(DF.format(Float.parseFloat(O_API.getOrderTotal()[i])));
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(OrderID.size() == 0)
                        {
                            TextView TV1 = findViewById(R.id.TV1);
                            TV1.setText("There are no active or completed orders to display here.");
                        }
                        else
                        {
                            MA[0] = new MyOrdersCustomView(MyOrders.this, OrderID, OrderType, OrderStatus, OrderTotal);
                            MyOrdersList.setAdapter(MA[0]);
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