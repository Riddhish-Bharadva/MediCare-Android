package com.rab.medicare;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSONConnection
{
    String SendData = "";
    String ResponseData = null;

    public JSONConnection(String SendData) {
        this.SendData = SendData;
    }

    public String JSONCon() throws Exception
    {
        try
        {
            URL API_URL = new URL(MainActivity.WebLink);
            HttpURLConnection API_Connection = (HttpURLConnection) API_URL.openConnection();
            API_Connection.setRequestMethod("POST");
            API_Connection.setRequestProperty("Content-Type", "application/json");
            API_Connection.setRequestProperty("Accept", "application/json");
            API_Connection.setDoOutput(true);
            API_Connection.setConnectTimeout(10000);
            try {
                DataOutputStream stream = new DataOutputStream(API_Connection.getOutputStream());
                stream.writeBytes(this.SendData);
                stream.flush();
            } catch (Exception e) {
                System.out.println("Exception2 : " + e);
            }
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(API_Connection.getInputStream()));
                StringBuffer SB = new StringBuffer();
                String Temp;
                while ((Temp = br.readLine()) != null) {
                    SB.append(Temp);
                }
                ResponseData = SB.toString();
            } catch (Exception e) {
                System.out.println("Exception3 : " + e);
            }
            API_Connection.disconnect();
        } catch (Exception e) {
            System.out.println("Exception1 : " + e);
        }
        System.out.println("Response Data : "+ResponseData);
        return ResponseData;
    }
}
