package com.rab.medicare;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageURLToBitmap
{
    public Bitmap Convert(String PassedURL) throws Exception
    {
        Bitmap ImageBitmap;
        java.net.URL url = new URL(PassedURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream input = connection.getInputStream();
        ImageBitmap = BitmapFactory.decodeStream(input);
        return ImageBitmap;
    }
}