package com.rab.medicare;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddToCart
{
    LoggedInDecision LID = new LoggedInDecision();
    public String AddProduct(String ProductID)
    {
        String Status = "";
        if(LID.getLoginStatus() != "")
        {
            API_Call HP = new API_Call(ProductID);
            Thread AddToCartThread = new Thread(HP);
            AddToCartThread.start();
        }
        else
        {
            Status = "NoUserLoggedIn";
        }
        return Status;
    }

    public class API_Call implements Runnable
    {
        private String ProductID = "";
        private String ResponseData = "";

        public API_Call(String ProductID)
        {
            this.ProductID = ProductID;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void run()
        {
            String SendData = "{\"function\":\"AddToCart\",\"userEmail\":\""+LID.getLoginStatus()+"\",\"ProductID\":\""+ProductID+"\"}";
            JSONConnection JSC = new JSONConnection(SendData);
            try
            {
                ResponseData = JSC.JSONCon();
                ObjectMapper OM = new ObjectMapper();
                API_Response API_Res = OM.readValue(ResponseData, API_Response.class);
                System.out.println(API_Res.getNotification());
            }
            catch(Exception e)
            {
                System.out.println("Exception : "+e);
            }
        }
    }
}